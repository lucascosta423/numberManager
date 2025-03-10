package com.main.numberManager.dtos.provedor;

import jakarta.persistence.Column;
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
public class ProvedorDTO {
    @NotBlank(message = "Nome não pode ser vazio ou nulo")
    @Column(nullable = false,unique = true,length = 150)
    private String nome;

    @NotBlank(message = "Contato não pode ser vazio")
    @Column(nullable = false,unique = true,length = 11)
    private String contato;

    @CNPJ
    @NotBlank(message = "Cnpj não pode ser vazio")
    @Column(nullable = false,unique = true,length = 14)
    private String documento;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "Email não pode ser vazio")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.com(\\\\.[A-Za-z]+)*$",
            message = "O e-mail deve terminar com .com"
    )
    @Column(nullable = false,unique = true,length = 30)
    private String email;
}
