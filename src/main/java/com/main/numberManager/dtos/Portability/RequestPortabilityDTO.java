package com.main.numberManager.dtos.Portability;

import java.util.List;

public record RequestPortabilityDTO(
         String razao,
         String documento,
         List<String> numeros
) {
}
