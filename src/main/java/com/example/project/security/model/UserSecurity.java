package com.example.project.security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Entity(name = "usr_security")
public class UserSecurity {
    @Id
    @SequenceGenerator(name = "SeqSequr", sequenceName = "usr_security_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "SeqSequr")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Roles role;

    @Column(name = "is_Blocked")
    private Boolean is_Blocked;

    @Column(name = "user_id")
    private Long user_id;
}
