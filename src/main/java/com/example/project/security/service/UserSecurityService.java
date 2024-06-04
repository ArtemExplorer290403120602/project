package com.example.project.security.service;

import com.example.project.exception.SameUserInDatabase;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.model.Roles;
import com.example.project.security.model.UserSecurity;
import com.example.project.security.model.dto.UserDto;
import com.example.project.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSecurityService {
    private final PasswordEncoder passwordEncoder;
    private final UserSecurityRepository userSecurityRepository;
    private UserRepository userRepository;

    @Autowired
    public UserSecurityService(PasswordEncoder passwordEncoder, UserSecurityRepository userSecurityRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registeredUser(UserDto userDto){
        Optional<UserSecurity> security = userSecurityRepository.findByLogin(userDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(userDto.getLogin());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setCity(userDto.getCity());
        user.setInteresting(userDto.getInteresting());
        User savedUser = userRepository.save(user);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setLogin(userDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userSecurity.setEmail(userDto.getEmail());
        userSecurity.setRole(Roles.USER);
        userSecurity.setUser_id(savedUser.getId());
        userSecurityRepository.save(userSecurity);
    }

    public UserDto getUserByLogin(String login) {
        UserSecurity userSecurity = userSecurityRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = userRepository.findById(userSecurity.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setSurname(user.getSurname());
        userDto.setAge(user.getAge());
        userDto.setCity(user.getCity());
        userDto.setInteresting(user.getInteresting());
        userDto.setLogin(userSecurity.getLogin());
        userDto.setEmail(userSecurity.getEmail());

        return userDto;
    }

    public Roles getRoleByLogin(String login) {
        UserSecurity userSecurity = userSecurityRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userSecurity.getRole();
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeUserByLogin(String login) {
        UserSecurity userSecurity = userSecurityRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userSecurityRepository.delete(userSecurity);
        // Дополнительные операции удаления в зависимости от логики вашего приложения
    }
}
