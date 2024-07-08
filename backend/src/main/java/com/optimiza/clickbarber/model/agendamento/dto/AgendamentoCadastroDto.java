package com.optimiza.clickbarber.model.agendamento.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentoCadastroDto {

    private ZonedDateTime dataHora;
    private BigDecimal valorTotal;
    private Integer tempoDuracaoEmMinutos;
    private Long barbeariaId;
    private Long clienteId;
    private List<Long> servicos;
    private List<Long> barbeiros;

}
