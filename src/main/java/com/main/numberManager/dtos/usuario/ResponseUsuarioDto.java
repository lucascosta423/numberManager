package com.main.numberManager.dtos.usuario;

import com.main.numberManager.models.UsuarioModel;

import java.util.UUID;

public record ResponseUsuarioDto(
        UUID id,
        String nome,
        String email,
        String usuario,
        String provedor_name
) {
    public static ResponseUsuarioDto fromEntity(UsuarioModel usuario) {
        return new ResponseUsuarioDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getUsuario(),
                usuario.getProvedor().getNome()
        );
    }
}
