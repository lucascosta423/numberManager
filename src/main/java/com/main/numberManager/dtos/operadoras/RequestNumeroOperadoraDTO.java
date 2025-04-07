package com.main.numberManager.dtos.operadoras;

public record RequestNumeroOperadoraDTO(
        String codigoNacional,
        String prefixo,
        String mcdu
) {
}
