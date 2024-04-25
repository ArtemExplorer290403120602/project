package com.example.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerGlobal {
    @ExceptionHandler(SameUserInDatabase.class)
    public ResponseEntity<String> handleSameUserInDatabase(SameUserInDatabase ex) {
        log.error("Registration problem! We already have user with login: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration problem! We already have user with login: " + ex.getMessage());
    }
}
