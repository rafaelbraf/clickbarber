package com.optimiza.clickbarber.model.servico.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class ServicoAtualizarDto {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer tempoDuracaoEmMinutos;
    private boolean ativo;

}
