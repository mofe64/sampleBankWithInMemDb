package com.example.digicoreassessment.models;

public enum Permission {
    ACCOUNT_READ("account:read"),
    ACCOUNT_WRITE("account:write"),
    TRANSACTION_READ("transaction:read"),
    TRANSACTION_WRITE("transaction:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
