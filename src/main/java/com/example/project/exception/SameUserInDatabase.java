package com.example.project.exception;

public class SameUserInDatabase extends RuntimeException {
    private final String message;
    public SameUserInDatabase(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Registration problem! We already have user with login: " + message;
    }
}
