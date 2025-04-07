package com.main.numberManager.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity(name = "solicitacaoPortabilidade")
@Table(name = "SO_Portabilidade")
public class SolicitacaoPortabilidadeModel {

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

    @OneToMany(mappedBy = "solicitacaoPortabilidade", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SolicitacaoNumeroModel> solicitacaoNumeroModel;
}
