package com.example.project.controller;

import com.example.project.model.Request;
import com.example.project.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/completed-requests")
public class CompletedRequestController {
    private RequestService requestService;

    @Autowired
    public CompletedRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    public String showCompletedRequests(Model model) {
        List<Request> completedRequests = requestService.getCompletedRequestsByUser();
        model.addAttribute("completedRequests", completedRequests);
        return "completed_applications";
    }

    @GetMapping("/{id}")
    public String showRequestDetails(@PathVariable Long id, Model model) {
        Request request = requestService.getRequestById(id);
        model.addAttribute("request", request);
        return "request_details";
    }
}
