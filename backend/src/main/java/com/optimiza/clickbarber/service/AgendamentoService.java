package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.Agendamento;
import com.optimiza.clickbarber.model.dto.agendamento.*;
import com.optimiza.clickbarber.repository.AgendamentoRepository;
import com.optimiza.clickbarber.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;
    private final BarbeariaService barbeariaService;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final BarbeiroService barbeiroService;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, AgendamentoMapper agendamentoMapper, BarbeariaService barbeariaService, ClienteService clienteService, ServicoService servicoService, BarbeiroService barbeiroService) {
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoMapper = agendamentoMapper;
        this.barbeariaService = barbeariaService;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.barbeiroService = barbeiroService;
    }

    public AgendamentoDto buscarPorId(Long id) {
        var agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.AGENDAMENTO, Constants.Attribute.ID, id.toString()));

        return agendamentoMapper.toDto(agendamento);
    }

    public List<AgendamentoRespostaDto> buscarPorIdExternoBarbearia(UUID idExternoBarbearia) {
        return agendamentoRepository.findByIdExternoBarbearia(idExternoBarbearia).stream()
                .map(agendamentoMapper::toAgendamentoRespostaDto)
                .toList();
    }

    public List<AgendamentoReduzidoDto> buscarReduzidoPorIdExternoBarberia(UUID idExternoBarbearia) {
        return agendamentoRepository.findByIdExternoBarbearia(idExternoBarbearia).stream()
                .map(agendamentoMapper::toAgendamentoReduzidoDto)
                .toList();
    }

    public List<Agendamento> buscarPorBarbeariaIdEDataHora(Integer barbeariaId, ZonedDateTime dataHora) {
        return agendamentoRepository.findByDataHoraAndBarbeariaId(dataHora, barbeariaId);
    }

    @Transactional
    public AgendamentoDto cadastrar(AgendamentoCadastroDto agendamentoCadastro) {
        var agendamento = Agendamento.builder()
                .dataHora(agendamentoCadastro.getDataHora())
                .valorTotal(agendamentoCadastro.getValorTotal())
                .tempoDuracaoEmMinutos(agendamentoCadastro.getTempoDuracaoEmMinutos())
                .build();

        var barbearia = barbeariaService.buscarPorId(agendamentoCadastro.getBarbeariaId());
        agendamento.setBarbearia(barbearia);

        var cliente = clienteService.buscarPorId(agendamentoCadastro.getClienteId());
        agendamento.setCliente(cliente);

        var servicos = agendamentoCadastro.getServicos().stream()
                .map(servicoService::buscarPorId)
                .collect(Collectors.toSet());
        agendamento.setServicos(servicos);

        var barbeiros = agendamentoCadastro.getBarbeiros().stream()
                .map(barbeiroService::buscarPorId)
                .collect(Collectors.toSet());
        agendamento.setBarbeiros(barbeiros);

        var agendamentoCadastrado = agendamentoRepository.save(agendamento);

        return agendamentoMapper.toDto(agendamentoCadastrado);
    }

    public AgendamentoDto atualizar(AgendamentoAtualizarDto agendamento) {
        var agendamentoCadastrado = agendamentoRepository.findById(agendamento.getId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.AGENDAMENTO, Constants.Attribute.ID, agendamento.getId().toString()));
        agendamentoCadastrado.setDataHora(agendamento.getDataHora());
        agendamentoCadastrado.setValorTotal(agendamento.getValorTotal());
        agendamentoCadastrado.setTempoDuracaoEmMinutos(agendamento.getTempoDuracaoEmMinutos());
        var agendamentoAtualizado = agendamentoRepository.save(agendamentoCadastrado);
        return agendamentoMapper.toDto(agendamentoAtualizado);
    }

    public void deletarPorId(Long id) {
        agendamentoRepository.deleteById(id);
    }
}
