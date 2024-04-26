package com.example.project.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login-user")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/registration"; // Перенаправление на страницу регистрации
    }
}


