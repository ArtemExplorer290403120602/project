package com.example.project.security.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RegistrationDto {

    @Size(min = 3, max = 25)
    private String login;

    @Size(min = 3, max = 25)
    @Pattern(regexp = "^A-Z.+$")
    private String password;

    @Size(min = 3, max = 25)
    @Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$") //  хороший пример example@example.com
    private String email;

    private String username;
    private String surname;
    private Integer age;
    private String city;
    private String interesting;
}
