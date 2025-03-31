package com.main.numberManager.dtos.numero;

import jakarta.validation.constraints.NotBlank;

public record RequestNumeroDTO(
        @NotBlank(message = "Cliente não pode ser vazio")
        String cliente,

        @NotBlank(message = "Documento não pode ser vazio")
        String documento,

        String status,

        Integer idProvedor
        )
{}
