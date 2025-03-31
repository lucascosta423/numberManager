package com.main.numberManager.dtos.operadoras;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOperadorasDto {
    private String nomePrestadora;
    private String cnpjPrestadora;
    private String uf;
    private String codigoNacional;
    private String prefixo;
    private String faixaInicial;
    private String faixaFinal;
    private String codigoCNL;
    private String nomeLocalidade;
    private String areaLocal;
    private String siglaAreaLocal;
    private String codigoAreaLocal;
    private String status;
}
