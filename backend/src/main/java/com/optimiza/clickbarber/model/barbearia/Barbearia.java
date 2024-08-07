package com.optimiza.clickbarber.model.barbearia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.formaspagamento.FormaPagamento;
import com.optimiza.clickbarber.model.horariofuncionamento.HorarioFuncionamento;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Table(name = "barbearias")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Barbearia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true, nullable = false)
    private UUID idExterno;

    private String cnpj;
    private String nome;
    private String endereco;
    private String telefone;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Servico> servicos;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("barbearia")
    private List<Barbeiro> barbeiros;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioFuncionamento> horarios;

    @OneToMany(mappedBy = "barbearia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormaPagamento> formasDePagamento;

    @PrePersist
    void gerarIdExterno() {
        if (isNull(idExterno)) {
            idExterno = UUID.randomUUID();
        }
    }

    public Long getId() {
        return id;
    }

}
