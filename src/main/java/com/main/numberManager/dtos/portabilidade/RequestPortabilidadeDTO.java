package com.main.numberManager.dtos.portabilidade;

import java.util.List;
import java.util.UUID;

public record RequestPortabilidadeDTO(
         String razao,
         String documento,
         UUID usuario,
         List<String> numero,
         Integer provedor
) {
}
