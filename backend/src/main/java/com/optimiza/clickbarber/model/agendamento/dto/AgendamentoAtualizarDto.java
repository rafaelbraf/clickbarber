package com.optimiza.clickbarber.model.agendamento.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentoAtualizarDto {

    private Long id;
    private ZonedDateTime dataHora;
    private BigDecimal valorTotal;
    private Integer tempoDuracaoEmMinutos;

}
