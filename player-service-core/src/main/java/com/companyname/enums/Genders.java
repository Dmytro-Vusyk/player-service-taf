package com.companyname.enums;

/**
 * The enum that represents player Genders.
 */
public enum Genders {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String value;

    Genders(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
