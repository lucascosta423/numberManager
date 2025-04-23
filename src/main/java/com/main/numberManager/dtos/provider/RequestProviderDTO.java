package com.main.numberManager.dtos.provider;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestProviderDTO {

    @NotBlank(message = "Nome não pode ser vazio ou nulo")
    private String nome;

    @NotBlank(message = "Contato não pode ser vazio")
    private String contato;

    @CNPJ
    @NotBlank(message = "Cnpj não pode ser vazio")
    private String documento;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "Email não pode ser vazio")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "O e-mail deve ser válido"
    )
    private String email;
}
