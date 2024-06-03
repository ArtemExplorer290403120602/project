package com.example.project.service;

import com.example.project.model.Request;
import com.example.project.repository.RequestRepository;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RequestService {
    private RequestRepository requestRepository;
    private UserSecurityRepository userSecurityRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, UserSecurityRepository userSecurityRepository) {
        this.requestRepository = requestRepository;
        this.userSecurityRepository = userSecurityRepository;
    }

    public void createRequest(Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Optional<UserSecurity> user = userSecurityRepository.findByLogin(login);
        if (user != null && user.isPresent()) {
            Long userSecurityId = user.get().getId();
            request.setLogin(login);
            request.setUser_security_id(userSecurityId);
            request.setData(new Timestamp(System.currentTimeMillis()));
            requestRepository.save(request);
            log.info("Request created: " + request);
        } else {
            log.error("User not found or invalid for login: " + login);
        }
    }

    public List<Request> getCompletedRequestsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Optional<UserSecurity> user = userSecurityRepository.findByLogin(login);

        if (user != null && user.isPresent()) {
            Long userSecurityId = user.get().getId();
            return requestRepository.findByUser_security_id(userSecurityId);
        } else {
            log.error("User not found or invalid for login: " + login);
            return Collections.emptyList(); // если пользователь не найден, возвращаем пустой список
        }
    }

    public Request getRequestById(Long id) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            return requestOpt.get();
        } else {
            // Можно выбрасывать исключение или вернуть пустой объект Request
            return new Request();
        }
    }

    public List<Request> allRequest() {
        return requestRepository.allRequest();
    }
}
