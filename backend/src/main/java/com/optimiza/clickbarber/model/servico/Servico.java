package com.optimiza.clickbarber.model.servico;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Table(name = "servicos")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_externo", unique = true, nullable = false)
    private UUID idExterno;

    private String nome;
    private BigDecimal preco;
    private Integer tempoDuracaoEmMinutos;

    @ManyToOne
    @JoinColumn(name = "barbearia_id")
    @JsonBackReference
    private Barbearia barbearia;

    private boolean ativo;

    @PrePersist
    void gerarIdExterno() {
        if (isNull(idExterno)) {
            idExterno = UUID.randomUUID();
        }
    }

}
