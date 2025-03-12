package com.main.numberManager.models.provedor;

import com.main.numberManager.models.numero.NumeroModel;
import com.main.numberManager.models.usuario.UsuarioModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;


@Entity(name = "provedor")
@Table(name = "TB_PROVEDOR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvedorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true,length = 150)
    private String nome;

    @Column(nullable = false,unique = true,length = 11)
    private String contato;

    @Column(nullable = false,unique = true,length = 15)
    private String documento;

    @Column(nullable = false,unique = true,length = 70)
    private String email;
}
