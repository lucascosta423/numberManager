package com.main.numberManager.dtos.operadoras;

public record ResponseNumeroPortabilidadeDTO(
        Integer codigoNacional,
        Integer prefixo,
        Integer codigoCnl,
        String nomePrestadora
) {
}
