package com.main.numberManager.model.solicitacaoPortabilidade;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SO_PORTABILIDADE")
public class Portabilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private List<String> numeros;

    private String razao;

    private String documento;

    private Integer idUsuario;

    private Integer idProvedor;

    private Integer codigoCnl;
}
