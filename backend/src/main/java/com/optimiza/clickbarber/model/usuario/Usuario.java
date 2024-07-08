package com.optimiza.clickbarber.model.usuario;

import com.optimiza.clickbarber.model.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

import static java.util.Objects.isNull;

@Table(name = "usuarios")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true, nullable = false)
    private UUID idExterno;

    private String email;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    void gerarIdExterno() {
        if (isNull(idExterno)) {
            idExterno = UUID.randomUUID();
        }
    }

}
