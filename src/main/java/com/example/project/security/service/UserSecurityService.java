package com.example.project.security.service;

import com.example.project.exception.SameUserInDatabase;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.model.Roles;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.model.dto.RegistrationDto;
import com.example.project.security.repository.UserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDto registrationDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByLogin(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }

        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setSurname(registrationDto.getSurname());
        user.setAge(registrationDto.getAge());
        user.setCity(registrationDto.getCity());
        user.setInteresting(registrationDto.getInteresting());
        User savedUser = userRepository.save(user);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setLogin(registrationDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userSecurity.setEmail(registrationDto.getEmail());
        userSecurity.setRole(Roles.USER);
        userSecurity.setUser_id(savedUser.getId());
        userSecurity.setIs_Blocked(false);
        userSecurityRepository.save(userSecurity);
    }
}
