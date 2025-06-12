package com.main.numberManager.Enuns;

public enum UserRole {
    ADMIN("Administrador"),
    USER("Usuario");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
