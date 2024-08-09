package com.optimiza.clickbarber.model.agendamento.dto;

import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaRespostaDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroRespostaDto;
import com.optimiza.clickbarber.model.formaspagamento.FormaPagamento;
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoRespostaDto;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroAgendamentoDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoDto;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioAgendamentoDto;
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

    private UUID idExterno;
    private ZonedDateTime dataHora;
    private BigDecimal valorTotal;
    private Integer tempoDuracaoEmMinutos;
    private BarbeariaRespostaDto barbearia;
    private ClienteDto cliente;
    private Set<ServicoDto> servicos;
    private Set<BarbeiroAgendamentoDto> barbeiros;
    private FormaPagamentoRespostaDto formaPagamento;
    private UsuarioAgendamentoDto criadoPor;

}
