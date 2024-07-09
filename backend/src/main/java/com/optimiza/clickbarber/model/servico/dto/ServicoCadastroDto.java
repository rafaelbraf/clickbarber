package com.optimiza.clickbarber.model.servico.dto;

import com.optimiza.clickbarber.model.barbearia.Barbearia;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class ServicoCadastroDto {

    private String nome;
    private BigDecimal preco;
    private Integer tempoDuracaoEmMinutos;
    private boolean ativo;
    private UUID idExternoBarbearia;

}
