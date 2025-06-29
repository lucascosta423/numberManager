package com.main.numberManager.domain.requestPortability;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.numberManager.Enuns.StatusPortability;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "solicitacaonumero")
@Table(name = "SO_Numero")
public class NumberForPortabilityModel {

    @Id
    private String id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private Integer codigoCnl;

    @Column(nullable = false)
    private String nomePrestadora;

    @Column(nullable = false)
    private String nomeLocalidade;

    @Column(nullable = false)
    private String areaLocal;

    private String dataAgendamento;

    private String horaAgendamento;

    private StatusPortability statusSolicitacao;

    @ManyToOne
    @JoinColumn(name = "port_ticket", nullable = false)
    @JsonIgnore
    private RequestPortabilityModel requestPortabilityModel;
}
