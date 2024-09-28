package com.companyname.enums;

public enum PlayerEditor {
    ADMIN("admin"),
    SUPERVISOR("supervisor");

    private final String value;

    PlayerEditor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
