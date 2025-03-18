package com.main.numberManager.dtos.portabilidade;

import com.main.numberManager.models.provedor.ProvedorModel;

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
