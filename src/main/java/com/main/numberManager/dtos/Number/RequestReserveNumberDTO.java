package com.main.numberManager.dtos.Number;

import java.util.Set;

public record RequestReserveNumberDTO (
        Integer provedor,
        Set<Integer> idsNumeros
){
}
