package com.optimiza.clickbarber.model.dto.servico;

import com.optimiza.clickbarber.model.Barbearia;
import lombok.*;

import javax.validation.constraints.NotNull;
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
