package com.example.project.service;

import com.example.project.model.Response;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;

    @Autowired
    public UserService(UserRepository userRepository,UserSecurityRepository userSecurityRepository) {
        this.userRepository = userRepository;
        this.userSecurityRepository=userSecurityRepository;
    }

    public List<UserSecurity> allUser() {
        return userSecurityRepository.allUser();
    }
}
