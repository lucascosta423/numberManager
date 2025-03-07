package com.main.numberManager.models.solicitacaoPortabilidade;


import com.main.numberManager.models.usuario.UsuarioModel;
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
public class PortabilidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private List<String> numeros;

    private String razao;

    private String documento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuarioModel;

    private Integer idProvedor;

    private Integer codigoCnl;
}
