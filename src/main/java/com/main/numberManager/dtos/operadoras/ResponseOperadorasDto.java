package com.main.numberManager.dtos.operadoras;

import com.main.numberManager.models.OperadorasModel;

public record ResponseOperadorasDto (
    String nomePrestadora,
    Integer codigoNacional,
    Integer prefixo,
    Integer faixaInicial,
    Integer faixaFinal,
    Integer codigoCNL,
    Integer codigoArea
){
    public static ResponseOperadorasDto fromEntity(OperadorasModel operadora) {
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
