package com.optimiza.clickbarber.model.agendamento.dto;

import com.optimiza.clickbarber.model.agendamento.Agendamento;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaMapper;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import com.optimiza.clickbarber.model.cliente.dto.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AgendamentoMapper {

    private final BarbeariaMapper barbeariaMapper;
    private final ClienteMapper clienteMapper;
    private final BarbeiroMapper barbeiroMapper;

    @Autowired
    public AgendamentoMapper(BarbeariaMapper barbeariaMapper, ClienteMapper clienteMapper, BarbeiroMapper barbeiroMapper) {
        this.barbeariaMapper = barbeariaMapper;
        this.clienteMapper = clienteMapper;
        this.barbeiroMapper = barbeiroMapper;
    }

    public AgendamentoDto toDto(Agendamento agendamento) {
        if (isNull(agendamento)) return null;

        var cliente = clienteMapper.toDto(agendamento.getCliente());
        var barbearia = barbeariaMapper.toDto(agendamento.getBarbearia());
        var barbeiros = barbeiroMapper.toSetAgendamentoDto(agendamento.getBarbeiros());

        return AgendamentoDto.builder()
                .id(agendamento.getId())
                .dataHora(agendamento.getDataHora())
                .valorTotal(agendamento.getValorTotal())
                .tempoDuracaoEmMinutos(agendamento.getTempoDuracaoEmMinutos())
                .cliente(cliente)
                .barbearia(barbearia)
                .servicos(agendamento.getServicos())
                .barbeiros(barbeiros)
                .build();
    }

    public AgendamentoRespostaDto toAgendamentoRespostaDto(Agendamento agendamento) {
        if (isNull(agendamento)) return null;

        var cliente = clienteMapper.toDto(agendamento.getCliente());
        var barbearia = barbeariaMapper.toDto(agendamento.getBarbearia());
        var barbeiros = barbeiroMapper.toSetAgendamentoDto(agendamento.getBarbeiros());

        return AgendamentoRespostaDto.builder()
                .id(agendamento.getId())
                .dataHora(agendamento.getDataHora())
                .valorTotal(agendamento.getValorTotal())
                .tempoDuracaoEmMinutos(agendamento.getTempoDuracaoEmMinutos())
                .cliente(cliente)
                .barbearia(barbearia)
                .barbeiros(barbeiros)
                .servicos(agendamento.getServicos())
                .build();
    }

    public AgendamentoReduzidoDto toAgendamentoReduzidoDto(Agendamento agendamento) {
        var nomeServicos = agendamento.getServicos().stream()
                .map(Servico::getNome)
                .toList();

        return AgendamentoReduzidoDto.builder()
                .idExterno(agendamento.getIdExterno())
                .nomeCliente(agendamento.getCliente().getNome())
                .dataHoraInicio(agendamento.getDataHora())
                .dataHoraFim(agendamento.getDataHora().plusMinutes(agendamento.getTempoDuracaoEmMinutos()))
                .servicos(nomeServicos)
                .build();
    }

}
