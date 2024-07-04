package com.optimiza.clickbarber.model.dto.servico;

import com.optimiza.clickbarber.model.Barbearia;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class ServicoCadastroDto {

    private String nome;
    private BigDecimal preco;
    private Integer tempoDuracaoEmMinutos;
    private boolean ativo;
    private Barbearia barbearia;

}
