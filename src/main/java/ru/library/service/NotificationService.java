package ru.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.library.model.Notification;
import ru.library.model.User;
import ru.library.repo.NotificationRepo;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private AuthService authService;

    public void makeNotification(User user, String message) {
        notificationRepo.save(new Notification(user, message));
    }

    public long getNotificationCountByUserId(User user) {
        try {
            final User usr = authService.getUserById(user.getId());
            return notificationRepo.countByUser(usr);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public List<Notification> getNotifications(User user) {
        try {
            final User entity = authService.getUserById(user.getId());
            final List<Notification> notifications = notificationRepo.findAllByUser(entity);
            notifications.forEach(n -> { if (!n.isRead()) n.setRead(true); });
            return notificationRepo.saveAll(notifications);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}
