package com.companyname.enums;

public enum UserRoles {
    USER("user");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
