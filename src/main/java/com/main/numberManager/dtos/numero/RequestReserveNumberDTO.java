package com.main.numberManager.dtos.numero;

import java.util.Set;

public record RequestReserveNumberDTO (
        Integer provedor,
        Set<Integer> idsNumeros
){
}
