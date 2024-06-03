package com.example.project.controller;

import com.example.project.security.model.Roles;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.model.dto.UserDto;
import com.example.project.security.service.UserSecurityService;
import com.example.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class MyProfileController {
    private final UserSecurityService userSecurityService;

    @Autowired
    public MyProfileController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }


    @GetMapping("/profile")
    public String profile(Model model,  Principal principal){
        UserDto userDto = userSecurityService.getUserByLogin(principal.getName());
        Roles role = userSecurityService.getRoleByLogin(principal.getName());
        model.addAttribute("userDto", userDto);
        model.addAttribute("role", role);
        if (role == Roles.USER) {
            return "profile";
        } else if (role == Roles.ADMIN) {
            return "admin_profile";
        } else if (role == Roles.MODERATION) {
            return "moderator_profile";
        } else {
            return "error-page";
        }
    }
}
