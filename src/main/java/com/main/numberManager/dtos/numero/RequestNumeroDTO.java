package com.main.numberManager.dtos.numero;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;

public record RequestNumeroDTO(
        @NotBlank(message = "Cliente não pode ser vazio")
        String cliente,

        @NotBlank(message = "Documento não pode ser vazio")
        String documento,

        String status,

        Integer idProvedor
        )
{}
