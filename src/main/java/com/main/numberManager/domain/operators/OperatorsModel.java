package com.main.numberManager.domain.operators;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Entity
@Table(name = "operadoras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperatorsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nomePrestadora;

    private String cnpjPrestadora;

    private String uf;

    private String codigoNacional;

    private String prefixo;

    private String faixaInicial;

    private String faixaFinal;

    private Integer codigoCnl;

    private String nomeLocalidade;

    private String areaLocal;

    private String siglaAreaLocal;

    private Integer codigoArea;

    private String status;
}
