package com.optimiza.clickbarber.model.agendamento.dto;

import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroAgendamentoDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentoDto {

    private Long id;
    private UUID idExterno;
    private ZonedDateTime dataHora;
    private BigDecimal valorTotal;
    private Integer tempoDuracaoEmMinutos;
    private BarbeariaDto barbearia;
    private ClienteDto cliente;
    private Set<Servico> servicos;
    private Set<BarbeiroAgendamentoDto> barbeiros;

}
