package com.main.numberManager.models.usuario;

import com.main.numberManager.models.provedor.ProvedorModel;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_USUARIOS")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private ProvedorModel provedorModel;
    private String nome;
    private String email;
    private String usuario;
    private String senha;
}
