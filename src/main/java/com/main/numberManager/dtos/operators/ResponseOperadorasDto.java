package com.main.numberManager.dtos.operators;

import com.main.numberManager.models.OperatorsModel;

public record ResponseOperadorasDto (
    String nomePrestadora,
    String codigoNacional,
    String prefixo,
    String faixaInicial,
    String faixaFinal,
    Integer codigoCNL,
    Integer codigoArea
){
    public static ResponseOperadorasDto fromEntity(OperatorsModel operadora) {
        return new ResponseOperadorasDto(
                operadora.getNomePrestadora(),
                operadora.getCodigoNacional(),
                operadora.getPrefixo(),
                operadora.getFaixaInicial(),
                operadora.getFaixaFinal(),
                operadora.getCodigoCnl(),
                operadora.getCodigoArea()
        );
    }
}
