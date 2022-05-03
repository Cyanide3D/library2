package ru.library.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.library.model.User;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) {
        Map<Object, Object> data = new HashMap<>();

        if (user != null) {
            data.put("profile", user);
        }

        model.addAttribute("frontendData", data);
        return "index";
    }

}
