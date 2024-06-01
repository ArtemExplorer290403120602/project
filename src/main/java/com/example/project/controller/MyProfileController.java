package com.example.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MyProfileController {

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }
}
