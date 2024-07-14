package com.optimiza.clickbarber.model.agendamento.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

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
    private UUID barbeariaIdExterno;
    private UUID clienteIdExterno;
    private List<UUID> servicosIdsExterno;
    private List<UUID> barbeirosIdsExterno;

}
