package com.example.project.security.controller;

import com.example.project.exception.SameUserInDatabase;
import com.example.project.security.model.dto.RegistrationDto;
import com.example.project.security.service.UserSecurityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserSecurityController {
    private final UserSecurityService userSecurityService;

    @Autowired
    public UserSecurityController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(RegistrationDto registrationDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(@ModelAttribute("registrationDto") @Valid RegistrationDto registrationDto) {
        userSecurityService.registration(registrationDto);
        return "redirect:/login-user"; // Redirect to login page after successful registration
    }
}
