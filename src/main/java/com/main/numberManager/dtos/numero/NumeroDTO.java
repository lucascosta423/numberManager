package com.main.numberManager.dtos.numero;

import java.time.LocalDateTime;

public record NumeroDTO(
        String cn,
        String prefixo,
        String mcdu,
        String area,
        String cliente,
        String documento,
        LocalDateTime dataAtivacao,
        LocalDateTime dataCancelamento,
        String status,
        Integer idProvedor
        )
{}
