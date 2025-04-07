package com.main.numberManager.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "solicitacaonumero")
@Table(name = "SO_Numero")
public class SolicitacaoNumeroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private Integer codigoCnl;

    @Column(nullable = false)
    private String nomePrestadora;

    private String dataAgendamento;

    private String horaAgendamento;

    private String status;

    @ManyToOne
    @JoinColumn(name = "port_ticket", nullable = false)
    @JsonIgnore
    private SolicitacaoPortabilidadeModel solicitacaoPortabilidadeModel;
}
