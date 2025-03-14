package com.main.numberManager.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioDTO(

        @NotBlank(message = "Nome não pode ser vazio ou nulo")
        String nome,

        @Email(message = "O e-mail deve ser válido")
        @NotBlank(message = "Email não pode ser vazio")
        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "O e-mail deve ser válido"
        )
        String email,

        @NotBlank(message = "Usuario não pode ser vazio")
        String usuario,

        String senha,

        Integer provedor
) {
        public UsuarioDTO(String senha, String usuario, String email, String nome) {
                this(nome, email, usuario, senha, 0);
        }
}
