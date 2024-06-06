package com.AuthService.entity;

public enum RoleType {
    CUSTOMER("ROLE_CUSTOMER"),
    EXPERT("ROLE_EXPERT"),
    ADMIN("ROLE_ADMIN");

    private String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
