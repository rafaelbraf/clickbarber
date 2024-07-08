package com.optimiza.clickbarber.model.agendamento.dto;

import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroAgendamentoDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgendamentoRespostaDto {

    private Long id;
    private ZonedDateTime dataHora;
    private BigDecimal valorTotal;
    private Integer tempoDuracaoEmMinutos;
    private ClienteDto cliente;
    private BarbeariaDto barbearia;
    private Set<BarbeiroAgendamentoDto> barbeiros;
    private Set<Servico> servicos;

}
