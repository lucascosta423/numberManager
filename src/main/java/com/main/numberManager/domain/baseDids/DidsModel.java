package com.main.numberManager.domain.baseDids;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.domain.providers.ProviderModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "numero")
@Table(name = "numero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DidsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 2)
    private String cn;

    @Column(nullable = false,length = 4)
    private String prefixo;

    @Column(nullable = false,length = 4)
    private String mcdu;

    @Column(nullable = false,length = 20)
    private String area;

    private String cliente;

    private String documento;

    private LocalDateTime dataResevada;

    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataAtivacao;

    private LocalDateTime dataUpload;
    @PrePersist
    private void onCreate(){
        this.dataUpload = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "id_provedor")
    private ProviderModel provedor;

    @Enumerated(EnumType.STRING)
    private Status status;


    public String getNumero(){
        return this.cn + this.prefixo + this.mcdu;
    }
}
