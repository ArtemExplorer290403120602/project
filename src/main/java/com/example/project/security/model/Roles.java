package com.example.project.security.model;

import java.util.Arrays;
import java.util.List;

public enum Roles {
    USER,ADMIN,MODERATION;
    public static List<String> getRoleList(Roles role) {
        return Arrays.asList(role.name());
    }
}
