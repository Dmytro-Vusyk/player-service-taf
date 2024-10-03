package com.companyname.enums;

public enum Environments {
    DEV("dev"),
    INT("int"),
    QA("qa");

    private final String envName;

    Environments(String envName) {
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
