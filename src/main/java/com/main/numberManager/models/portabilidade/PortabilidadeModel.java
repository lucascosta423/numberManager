package com.main.numberManager.models.portabilidade;


import com.main.numberManager.models.cnl.CnlGeralModel;
import com.main.numberManager.models.provedor.ProvedorModel;
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

    @ManyToOne
    @JoinColumn(name = "numero_id", nullable = false)
    private CnlGeralModel cnl;
}
