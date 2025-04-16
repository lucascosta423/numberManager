package com.main.numberManager.dtos.portabilidade;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.models.SolicitacaoNumeroModel;
import com.main.numberManager.models.SolicitacaoPortabilidadeModel;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseSolicitacaoPortabilidadeDto(
        String id,
        String razao,
        String documento,
        String usuario,
        String provedor,
        LocalDateTime dataCriado,
        LocalDateTime dataFinalizado,
        Status status,
        List<SolicitacaoNumeroModel> solicitacoes
) {
    public static ResponseSolicitacaoPortabilidadeDto fromEntity(SolicitacaoPortabilidadeModel portabilidadeModel) {
        return new ResponseSolicitacaoPortabilidadeDto(
                portabilidadeModel.getId(),
                portabilidadeModel.getRazao(),
                portabilidadeModel.getDocumento(),
                portabilidadeModel.getUsuario().getUsuario(),
                portabilidadeModel.getProvedor().getNome(),
                portabilidadeModel.getDataCriado(),
                portabilidadeModel.getDataFinalizado(),
                portabilidadeModel.getStatus(),
                portabilidadeModel.getSolicitacaoNumeroModel()
        );
    }
}
