package ru.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.library.model.Notification;
import ru.library.model.User;
import ru.library.service.NotificationService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(@AuthenticationPrincipal User user) {
        final List<Notification> notifications = service.getNotifications(user);
        Collections.reverse(notifications);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getNotificationCount(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getNotificationCountByUserId(user));
    }

}
