package com.example.project.security.service;

import com.example.project.exception.SameUserInDatabase;
import com.example.project.repository.UserRepository;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.model.dto.RegistrationDto;
import com.example.project.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserSecurityService {
    private final PasswordEncoder passwordEncoder;
    private final UserSecurityRepository userSecurityRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(PasswordEncoder passwordEncoder, UserSecurityRepository userSecurityRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
    }

    private void registration(RegistrationDto registrationDto){
        Optional<UserSecurity> security = userSecurityRepository.findByUserLogin(registrationDto.getLogin());
        if(security.isPresent()){
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
    }
}
