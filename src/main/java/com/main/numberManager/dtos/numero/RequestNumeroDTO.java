package com.main.numberManager.dtos.numero;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record RequestNumeroDTO(
        String cliente,
        String documento,
        LocalDateTime dataAtivacao,
        LocalDateTime dataCancelamento,
        String status,
        Integer idProvedor
        )
{}
