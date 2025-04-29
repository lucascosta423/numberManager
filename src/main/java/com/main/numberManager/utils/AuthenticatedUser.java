package com.main.numberManager.utils;

import com.main.numberManager.models.UsuarioModel;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {


    public static UsuarioModel user() {
        return (UsuarioModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
