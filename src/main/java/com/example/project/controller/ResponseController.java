package com.example.project.controller;

import com.example.project.model.Response;
import com.example.project.repository.ResponseRepository;
import com.example.project.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ResponseController {
    private ResponseService responseService;


    @Autowired
    public ResponseController(ResponseService responseService,ResponseRepository responseRepository) {
        this.responseService = responseService;
    }

    @GetMapping("/requests/create-response")
    public String createResponseForm(@RequestParam Long requestId, Model model) {
        model.addAttribute("response", new Response());
        model.addAttribute("requestId", requestId);
        return "create_response";
    }

    @PostMapping("/requests/create-response")
    public String createResponseSubmit(@ModelAttribute Response response, @RequestParam Long requestId) {
        responseService.createResponse(response.getResponse_text(), requestId);
        return "redirect:/requests/all";
    }

    @GetMapping("/responses/all")
    public String getAllResponses(Model model) {
        List<Response> responses = responseService.allResponses();
        model.addAttribute("responses", responses);
        return "user_response";
    }
}
