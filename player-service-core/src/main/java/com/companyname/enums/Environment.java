package com.companyname.enums;

public enum Environment {
    DEV("dev"),
    INT("int"),
    QA("qa");

    private final String envName;

    Environment(String envName) {
        this.envName = envName;
    }

    public String getValue() {
        return this.envName;
    }

    @Override
    public String toString() {
        return this.envName;
    }
}
