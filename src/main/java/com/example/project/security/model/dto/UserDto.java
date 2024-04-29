package com.example.project.security.model.dto;

import com.example.project.annotation.PasswordMatches;
import com.example.project.annotation.ValidEmail;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@PasswordMatches
public class UserDto {
    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;


    private String username;
    private String surname;
    private Integer age;
    private String city;
    private String interesting;
}
