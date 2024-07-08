package com.optimiza.clickbarber.model.cliente;

import com.optimiza.clickbarber.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.Objects.isNull;

@Table(name = "clientes")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true, nullable = false)
    private UUID idExterno;

    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private String celular;

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
