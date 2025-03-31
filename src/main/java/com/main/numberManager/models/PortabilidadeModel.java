package com.main.numberManager.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "portabilidade")
@Table(name = "portabilidade")
public class PortabilidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String numero;

    private String razao;

    private String documento;

    private String usuario;

    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private ProvedorModel provedor;

    private Integer cnl;
}
