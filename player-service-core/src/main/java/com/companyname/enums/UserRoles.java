package com.companyname.enums;

/**
 * The enum that represents User roles.
 */
public enum UserRoles {
    USER("user"),
    ADMIN("admin");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
