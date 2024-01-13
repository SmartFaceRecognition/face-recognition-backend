package com.Han2m.portLogistics.admin.domain;

public enum Role {
    ROLE_WORKER("WORKER"),
    ROLE_ADMIN("ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value(){
        return role;
    }
}
