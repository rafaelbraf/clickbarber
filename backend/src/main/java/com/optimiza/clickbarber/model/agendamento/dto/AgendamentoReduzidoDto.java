package com.optimiza.clickbarber.model.agendamento.dto;

import lombok.*;

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
public class AgendamentoReduzidoDto {

    private UUID idExterno;
    private String nomeCliente;
    private List<String> servicos;
    private ZonedDateTime dataHoraInicio;
    private ZonedDateTime dataHoraFim;

}
