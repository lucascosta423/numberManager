package com.main.numberManager.models.basesCnl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BASE_CNL_GERAL")
public class CnlGeral {
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
    private String codigoCNL;
    private String nomeLocalidade;
    private String areaLocal;
    private String siglaAreaLocal;
    private String codigoAreaLocal;
    private String status;
}

