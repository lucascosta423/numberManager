package com.main.numberManager.dtos.portabilidade;

public record RequestPortabilidadeDTO(
         Integer codigoNacional,
         Integer prefixo,
         Integer mcdu,
         String razao,
         String documento,
         Integer usuario,
         Integer provedor
) {
}
