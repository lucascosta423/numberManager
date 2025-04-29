package com.main.numberManager.Enuns;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    SUPER_ADMIN("super_admin");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
