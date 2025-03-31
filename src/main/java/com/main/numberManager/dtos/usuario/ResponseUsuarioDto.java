package com.main.numberManager.dtos.usuario;

import com.main.numberManager.models.UsuarioModel;

public record ResponseUsuarioDto(
        Integer id,
        String nome,
        String email,
        String usuario,
        Integer provedor_Id,
        String provedor_name
) {
    public static ResponseUsuarioDto fromEntity(UsuarioModel usuario) {
        return new ResponseUsuarioDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getUsuario(),
                usuario.getProvedor().getId(),
                usuario.getProvedor().getNome()
        );
    }
}
