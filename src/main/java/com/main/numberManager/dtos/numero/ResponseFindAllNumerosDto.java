package com.main.numberManager.dtos.numero;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.models.NumeroModel;

import java.time.LocalDateTime;

public record ResponseFindAllNumerosDto(
        Integer id,
        String cn,
        String prefixo,
        String mcdu,
        String area,
        String cliente,
        String documento,
        LocalDateTime dataAtivacao,
        String provedor,
        Status status
) {
    public static ResponseFindAllNumerosDto fromEntity(NumeroModel numeroModel) {
        return new ResponseFindAllNumerosDto(
                numeroModel.getId(),
                numeroModel.getCn(),
                numeroModel.getPrefixo(),
                numeroModel.getMcdu(),
                numeroModel.getArea(),
                numeroModel.getCliente(),
                numeroModel.getDocumento(),
                numeroModel.getDataAtivacao(),
                numeroModel.getProvedor()  != null ? numeroModel.getProvedor().getNome() : "",
                numeroModel.getStatus()
        );
    }
}
