package com.example.project.security.service;

import com.example.project.security.model.UserSecurity;
import com.example.project.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailSecurityService implements UserDetailsService {
    private final UserSecurityRepository userRepository;

    @Autowired
    public UserDetailSecurityService(UserSecurityRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserSecurity> securityInfoOptional = userRepository.findByLogin(username);
        if (securityInfoOptional.isEmpty()){
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        UserSecurity user = securityInfoOptional.get();

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}
