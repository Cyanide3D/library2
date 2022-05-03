package ru.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.library.model.User;
import ru.library.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody User user) {
        service.save(user);
        return ResponseEntity.ok().build();
    }

}
