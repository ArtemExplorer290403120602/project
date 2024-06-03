package com.example.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "responses")
public class Response {
    @Id
    @SequenceGenerator(name = "ResGenSeq",sequenceName = "responses_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "ResGenSeq")
    private Long id;
    @Column(name = "request_id")
    private Long request_id;
    @Column(name = "user_id")
    private Long user_security_id;
    @Column(name = "response_text")
    private String response_text;
    @Column(name = "response_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp response_date;
    @Column(name = "login")
    private String login;
    @Column(name = "model_name")
    private String model_name;
}
