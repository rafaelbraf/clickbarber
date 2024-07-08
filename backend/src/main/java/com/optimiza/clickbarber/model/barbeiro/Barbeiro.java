package com.optimiza.clickbarber.model.barbeiro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.optimiza.clickbarber.model.usuario.Usuario;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import static java.util.Objects.isNull;

@Table(name = "barbeiros")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true, nullable = false)
    private UUID idExterno;

    private String cpf;
    private String nome;
    private String celular;
    private boolean admin;
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "barbearia_id")
    @JsonIgnoreProperties("barbeiros")
    private Barbearia barbearia;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @PrePersist
    void gerarIdExterno() {
        if (isNull(idExterno)) {
            idExterno = UUID.randomUUID();
        }
    }

}
