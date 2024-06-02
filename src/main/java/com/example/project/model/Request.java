package com.example.project.model;

import com.example.project.security.model.UserSecurity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "requests")
public class Request {
    @Id
    @SequenceGenerator(name = "ReqSeqGen", sequenceName = "requests_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "ReqSeqGen")
    private Long id;
    @Column(name = "model_name")
    private String model_name;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp data;
    @Column(name = "text")
    private String text;
    @Column(name = "login")
    private String login;
    @Column(name = "user_security_id")
    private Long user_security_id;

}
