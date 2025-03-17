package com.main.numberManager.dtos.portabilidade;

import com.main.numberManager.models.provedor.ProvedorModel;

public record RequestPortabilidadeDTO(
         String numero,
         String razao,
         String documento,
         Integer usuario,
         Integer provedor
) {
}
