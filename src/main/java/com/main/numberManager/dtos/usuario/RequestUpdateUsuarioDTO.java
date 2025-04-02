package com.main.numberManager.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RequestUpdateUsuarioDTO {

        String nome;
        @Email(message = "O e-mail deve ser válido")

        @Pattern(
                regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "O e-mail deve ser válido"
        )
        String email;

        String senha;

}
