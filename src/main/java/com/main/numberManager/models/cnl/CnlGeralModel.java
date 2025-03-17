package com.main.numberManager.models.cnl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;





@Entity
@Table(name = "cnl_geral_model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CnlGeralModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nomePrestadora;

    private String cnpjPrestadora;

    private String uf;

    @Column(precision = 10, scale = 0)
    private Integer codigoNacional;

    @Column(precision = 10, scale = 0)
    private Integer prefixo;


    private Integer faixaInicial;


    private Integer faixaFinal;

    private Integer codigoCnl;

    private String nomeLocalidade;

    private String areaLocal;

    private String siglaAreaLocal;


    private Integer codigoArea;

    private String status;

}
