package com.example.project.controller;

import com.example.project.model.Request;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.repository.UserSecurityRepository;
import com.example.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/requests")
public class RequestController {
    private RequestService requestService;
    private UserSecurityRepository userSecurityRepository;

    @Autowired
    public RequestController(RequestService requestService,UserSecurityRepository userSecurityRepository) {
        this.requestService = requestService;
        this.userSecurityRepository=userSecurityRepository;
    }


    @GetMapping("/create")
    public String showCreateRequestForm(Model model) {
        model.addAttribute("request", new Request());
        return "create_request";
    }

    @PostMapping("/create")
    public String createRequest(@ModelAttribute("request") Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Optional<UserSecurity> user = userSecurityRepository.findByLogin(login);
        if (user != null) {
            Long userSecurityId = user.get().getId();
            request.setLogin(login);
            request.setUser_security_id(userSecurityId);
            requestService.createRequest(request);
            return "redirect:/profile";
        } else {
            // Обработка ситуации, когда пользователь не найден
            return "redirect:/error";
        }
    }

    @GetMapping("/all")
    public String showAllRequests(Model model) {
        List<Request> requests = requestService.allRequest(); // Изменение вызова метода
        model.addAttribute("requests", requests);
        return "all_requests";
    }

    @GetMapping("/details/{id}")
    public String showRequestDetails(@PathVariable("id") Long id, Model model) {
        Request request = requestService.getRequestById(id);
        model.addAttribute("request", request);
        return "request_details_admin";
    }

    @GetMapping("/all_moderator")
    public String showAllRequestsModerator(Model model) {
        List<Request> requests = requestService.allRequest(); // Изменение вызова метода
        model.addAttribute("requests", requests);
        return "request_moderator";
    }

    @GetMapping("/moderator/details/{id}")
    public String showRequestDetailModerator(@PathVariable("id") Long id, Model model) {
        Request request = requestService.getRequestById(id);
        model.addAttribute("request", request);
        return "request_info_moderator";
    }

    @GetMapping("/delete/{id}")
    public String deleteRequest(@PathVariable("id") Long id) {
        requestService.deleteRequestById(id);
        return "redirect:/requests/all_moderator";
    }
}
