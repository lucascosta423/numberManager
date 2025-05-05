package com.main.numberManager.dtos.Number;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.models.NumeroModel;

import java.time.LocalDateTime;

public record ResponseAllNumbersDto(
        Integer id,
        String numero,
        String area,
        String cliente,
        String documento,
        LocalDateTime dataAtivacao,
        LocalDateTime dataSolicitacao,
        LocalDateTime dataResevada,
        String provedor,
        Status status
) {
    public static ResponseAllNumbersDto fromEntity(NumeroModel numeroModel) {
        return new ResponseAllNumbersDto(
                numeroModel.getId(),
                numeroModel.getNumero(),
                numeroModel.getArea(),
                numeroModel.getCliente(),
                numeroModel.getDocumento(),
                numeroModel.getDataAtivacao(),
                numeroModel.getDataSolicitacao(),
                numeroModel.getDataResevada(),
                numeroModel.getProvedor()  != null ? numeroModel.getProvedor().getNome() : "",
                numeroModel.getStatus()
        );
    }
}
