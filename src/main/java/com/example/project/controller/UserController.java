package com.example.project.controller;

import com.example.project.security.model.UserSecurity;
import com.example.project.security.model.dto.UserDto;
import com.example.project.security.service.UserSecurityService;
import com.example.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserSecurityService userSecurityService;

    @Autowired
    public UserController(UserService userService, UserSecurityService userSecurityService) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
    }

    @GetMapping("/all")
    public String getAllResponses(Model model) {
        List<UserSecurity> userSecur =userService.allUser();
        model.addAttribute("users", userSecur);
        return "user_page";
    }

    @GetMapping("/details/{login}")
    public String getUserDetails(@PathVariable String login, Model model) {
        UserDto userDto = userSecurityService.getUserByLogin(login);
        model.addAttribute("userDetails", userDto);
        return "user_info";
    }

    @PostMapping("/remove/{login}")
    public String removeUser(@PathVariable String login) {
        userSecurityService.removeUserByLogin(login);
        return "redirect:/user/all";
    }
}
