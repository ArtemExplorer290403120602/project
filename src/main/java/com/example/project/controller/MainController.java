package com.example.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class MainController {
    @GetMapping("/")
    public String mainPage(Model model, Principal principal){
        if (principal == null) {
            // Пользователь не авторизован, возвращаем модель с ссылками на login, register и developer
            model.addAttribute("showProfileLink", false);
        } else {
            // Пользователь авторизован, возвращаем модель с ссылкой на profile
            model.addAttribute("showProfileLink", true);
            model.addAttribute("username", principal.getName()); // Добавляем имя пользователя в модель
        }
        return "mainPage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
