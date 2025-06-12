package com.main.numberManager.utils;

import com.main.numberManager.domain.users.UserModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    public UserModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserModel) {
            return (UserModel) authentication.getPrincipal();
        }
        return null;
    }

    public boolean isAdmin() {
        var usuario = getCurrentUser();

        return usuario.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }
}
