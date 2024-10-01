package com.companyname.enums;

/**
 * The enum that represents Player editors.
 */
public enum PlayerEditors {
    ADMIN("admin"),
    SUPERVISOR("supervisor");

    private final String value;

    PlayerEditors(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
