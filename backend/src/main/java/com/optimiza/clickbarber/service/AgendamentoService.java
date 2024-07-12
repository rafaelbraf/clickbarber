package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.EntidadeNaoPerteceABarbeariaException;
import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.agendamento.Agendamento;
import com.optimiza.clickbarber.model.agendamento.dto.*;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.repository.AgendamentoRepository;
import com.optimiza.clickbarber.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
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

    public AgendamentoDto buscarPorIdExterno(UUID idExterno) {
        var agendamento = agendamentoRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.AGENDAMENTO, Constants.Attribute.ID_EXTERNO, idExterno.toString()));
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
        var agendamento = montarAgendamentoComInformacoesIniciais(agendamentoCadastro);

        var barbearia = barbeariaService.buscarPorId(agendamentoCadastro.getBarbeariaId());
        agendamento.setBarbearia(barbearia);

        var cliente = clienteService.buscarPorId(agendamentoCadastro.getClienteId());
        agendamento.setCliente(cliente);

        var servicos = buscarServicosDaBarbearia(agendamentoCadastro.getServicos(), barbearia);
        agendamento.setServicos(servicos);

        var barbeiros = buscarBarbeirosDaBarbearia(agendamentoCadastro.getBarbeiros(), barbearia);
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

    private Set<Servico> buscarServicosDaBarbearia(List<Long> servicosId, Barbearia barbearia) {
        return servicosId.stream()
                .map(servicoId -> {
                    var servico = servicoService.buscarPorId(servicoId);
                    if (!servico.getBarbearia().getId().equals(barbearia.getId())) {
                        throw new EntidadeNaoPerteceABarbeariaException(Constants.Entity.SERVICO, Constants.Attribute.ID, servicoId.toString());
                    }

                    return servico;
                })
                .collect(Collectors.toSet());
    }

    private Set<Barbeiro> buscarBarbeirosDaBarbearia(List<Long> barbeirosId, Barbearia barbearia) {
        return barbeirosId.stream()
                .map(barbeiroId -> {
                    var barbeiro = barbeiroService.buscarPorId(barbeiroId);
                    if (!barbeiro.getBarbearia().getId().equals(barbearia.getId())) {
                        throw new EntidadeNaoPerteceABarbeariaException(Constants.Entity.BARBEIRO, Constants.Attribute.ID, barbeiroId.toString());
                    }

                    return barbeiro;
                })
                .collect(Collectors.toSet());
    }

    private Agendamento montarAgendamentoComInformacoesIniciais(AgendamentoCadastroDto agendamentoCadastro) {
        return Agendamento.builder()
                .dataHora(agendamentoCadastro.getDataHora())
                .valorTotal(agendamentoCadastro.getValorTotal())
                .tempoDuracaoEmMinutos(agendamentoCadastro.getTempoDuracaoEmMinutos())
                .build();
    }
}
