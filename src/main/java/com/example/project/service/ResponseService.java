package com.example.project.service;

import com.example.project.model.Request;
import com.example.project.model.Response;
import com.example.project.repository.RequestRepository;
import com.example.project.repository.ResponseRepository;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseService {

    private ResponseRepository responseRepository;
    private UserSecurityRepository userSecurityRepository;
    private RequestRepository requestRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, UserSecurityRepository userSecurityRepository, RequestRepository requestRepository) {
        this.responseRepository = responseRepository;
        this.userSecurityRepository = userSecurityRepository;
        this.requestRepository = requestRepository;
    }

    public void createResponse(String responseText, Long requestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Optional<UserSecurity> userSecurityOptional = userSecurityRepository.findByLogin(login);
        UserSecurity userSecurity = userSecurityOptional.orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Request> requestOptional = requestRepository.findById(requestId);
        Request request = requestOptional.orElseThrow(() -> new RuntimeException("Request not found"));

        Response response = new Response();
        response.setRequest_id(requestId);
        response.setUser_security_id(userSecurity.getId());
        response.setResponse_text(responseText);
        response.setResponse_date(new Timestamp(System.currentTimeMillis()));
        response.setLogin(login);
        response.setModel_name(request.getModel_name());

        responseRepository.save(response);
    }

    public List<Response> allResponses() {
        return responseRepository.allResponses();
    }

    public void deleteResponse(Long responseId) {
        responseRepository.deleteById(responseId);
    }
}
