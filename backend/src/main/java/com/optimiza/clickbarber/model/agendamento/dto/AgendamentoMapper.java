package com.optimiza.clickbarber.model.agendamento.dto;

import com.optimiza.clickbarber.model.agendamento.Agendamento;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaMapper;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import com.optimiza.clickbarber.model.cliente.dto.ClienteMapper;
import com.optimiza.clickbarber.model.servico.dto.ServicoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AgendamentoMapper {

    private final BarbeariaMapper barbeariaMapper;
    private final ClienteMapper clienteMapper;
    private final BarbeiroMapper barbeiroMapper;
    private final ServicoMapper servicoMapper;

    @Autowired
    public AgendamentoMapper(BarbeariaMapper barbeariaMapper, ClienteMapper clienteMapper, BarbeiroMapper barbeiroMapper, ServicoMapper servicoMapper) {
        this.barbeariaMapper = barbeariaMapper;
        this.clienteMapper = clienteMapper;
        this.barbeiroMapper = barbeiroMapper;
        this.servicoMapper = servicoMapper;
    }

    public AgendamentoDto toDto(Agendamento agendamento) {
        if (isNull(agendamento)) return null;

        var cliente = clienteMapper.toDto(agendamento.getCliente());
        var barbearia = barbeariaMapper.toRespostaDto(agendamento.getBarbearia());
        var barbeiros = barbeiroMapper.toSetAgendamentoDto(agendamento.getBarbeiros());
        var servicos = servicoMapper.toSetDto(agendamento.getServicos());

        return AgendamentoDto.builder()
                .idExterno(agendamento.getIdExterno())
                .dataHora(agendamento.getDataHora())
                .valorTotal(agendamento.getValorTotal())
                .tempoDuracaoEmMinutos(agendamento.getTempoDuracaoEmMinutos())
                .cliente(cliente)
                .barbearia(barbearia)
                .servicos(servicos)
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
