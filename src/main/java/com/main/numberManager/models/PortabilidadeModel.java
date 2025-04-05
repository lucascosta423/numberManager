package com.main.numberManager.models;


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
@Entity(name = "portabilidade")
@Table(name = "portabilidade")
public class PortabilidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String razao;

    private String documento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private ProvedorModel provedor;

    @OneToMany(mappedBy = "portabilidadeModel", cascade = CascadeType.ALL)
    private List<NumeroPortabilidadeModel> numeroPortabilidadeModel;
}
