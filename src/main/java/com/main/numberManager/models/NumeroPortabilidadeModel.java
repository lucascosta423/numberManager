package com.main.numberManager.models;


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
@Entity(name = "numeroportabilidade")
@Table(name = "numeroportabilidade")
public class NumeroPortabilidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer codigoNacional;

    @Column(nullable = false)
    private Integer prefixo;

    @Column(nullable = false)
    private Integer codigoCnl;

    @Column(nullable = false)
    private String nomePrestadora;

    @ManyToOne
    @JoinColumn(name = "port_ticket", nullable = false)
    private PortabilidadeModel portabilidadeModel;


    @Override
    public String toString() {
        return "NumeroPortabilidadeModel{" +
                "id=" + id +
                ", codigoNacional=" + codigoNacional +
                ", prefixo=" + prefixo +
                ", codigoCnl=" + codigoCnl +
                ", nomePrestadora='" + nomePrestadora + '\'' +
                ", portabilidadeModel=" + portabilidadeModel +
                '}';
    }
}
