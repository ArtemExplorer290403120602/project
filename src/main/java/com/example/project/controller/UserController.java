package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAllUser(ModelMap modelMap) {
        List<User> users = userService.getAllUsers();
        modelMap.addAttribute("users", users);
        return users.isEmpty() ? "error" : "main";
    }

    @GetMapping("/id")
    public String getUserById(@RequestParam("id") Long id, ModelMap modelMap) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            modelMap.addAttribute("user", user.get());
            return "get_user";
        }
        return "error";
    }

    @GetMapping("/delete/id")
    public String deleteUserById(@RequestParam("id") Long id,ModelMap modelMap){
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            log.info("User delete by id: " + id);
            modelMap.addAttribute("deletedUser", user.get());
            return "get_deleted_user";
        }
        return "error";
    }
}
