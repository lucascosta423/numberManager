package com.main.numberManager.models.usuario;

import com.main.numberManager.models.portabilidade.PortabilidadeModel;
import com.main.numberManager.models.provedor.ProvedorModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "usuario")
@Table(name = "tb_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    @Column(nullable = false, unique = true)
    private String usuario;

    private String senha;

    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private ProvedorModel provedor;
}
