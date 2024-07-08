package com.optimiza.clickbarber.model.servico.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ServicoDto {

    private UUID idExterno;
    private String nome;
    private BigDecimal preco;
    private Integer tempoDuracaoEmMinutos;
    private boolean ativo;

}
