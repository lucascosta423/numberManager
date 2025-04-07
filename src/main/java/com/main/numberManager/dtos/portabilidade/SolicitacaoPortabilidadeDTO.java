package com.main.numberManager.dtos.portabilidade;

import java.util.List;
import java.util.UUID;

public record SolicitacaoPortabilidadeDTO(
         String razao,
         String documento,
         UUID usuario,
         List<String> numeros,
         Integer provedor
) {
}
