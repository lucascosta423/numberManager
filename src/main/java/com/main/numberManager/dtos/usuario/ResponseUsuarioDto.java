package com.main.numberManager.dtos.usuario;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.domain.users.UserModel;

import java.util.UUID;

public record ResponseUsuarioDto(
        UUID id,
        String nome,
        String email,
        String usuario,
        String role,
        Status status,
        String provedor_name
) {
    public static ResponseUsuarioDto fromEntity(UserModel usuario) {
        return new ResponseUsuarioDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getUsuario(),
                usuario.getRole().name(),
                usuario.getStatus(),
                usuario.getProvedor()  != null ? usuario.getProvedor().getNome() : ""
        );
    }
}
