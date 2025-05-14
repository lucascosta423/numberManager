package com.main.numberManager.dtos.Number;

import jakarta.validation.constraints.NotBlank;

public record RequestNumberUpdateDTO(
        @NotBlank(message = "Cliente não pode ser vazio")
        String cliente,

        @NotBlank(message = "Documento não pode ser vazio")
        String documento
)
{}
