package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.CadastroAgendamentoException;
import com.optimiza.clickbarber.exception.EntidadeNaoPerteceABarbeariaException;
import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.agendamento.Agendamento;
import com.optimiza.clickbarber.model.agendamento.dto.*;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.repository.AgendamentoRepository;
import com.optimiza.clickbarber.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendamentoMapper agendamentoMapper;
    private final BarbeariaService barbeariaService;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final BarbeiroService barbeiroService;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, AgendamentoMapper agendamentoMapper, BarbeariaService barbeariaService, ClienteService clienteService, ServicoService servicoService, BarbeiroService barbeiroService, BarbeiroMapper barbeiroMapper) {
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
        requireNonNull(agendamentoCadastro.getDataHora(), "Data e hora não podem ser nulos.");

        try {
            var agendamento = montarAgendamentoComInformacoesIniciais(agendamentoCadastro);

            var barbearia = barbeariaService.buscarPorIdExterno(agendamentoCadastro.getBarbeariaIdExterno());
            agendamento.setBarbearia(barbearia);

            var cliente = clienteService.buscarPorIdExterno(agendamentoCadastro.getClienteIdExterno());
            agendamento.setCliente(cliente);

            var servicos = buscarServicosDaBarbearia(agendamentoCadastro.getServicosIdsExterno(), barbearia);
            agendamento.setServicos(servicos);

            var barbeiros = buscarBarbeirosDaBarbearia(agendamentoCadastro.getBarbeirosIdsExterno(), barbearia);
            agendamento.setBarbeiros(barbeiros);

            var agendamentoCadastrado = agendamentoRepository.save(agendamento);

            clienteService.inserirBarbearia(cliente, barbearia);

            return agendamentoMapper.toDto(agendamentoCadastrado);
        } catch (Exception e) {
            throw new CadastroAgendamentoException(e.getMessage());
        }
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

    private Set<Servico> buscarServicosDaBarbearia(List<UUID> servicosIdsExterno, Barbearia barbearia) {
        return servicosIdsExterno.stream()
                .map(servicoIdExterno -> {
                    var servico = servicoService.buscarPorIdExterno(servicoIdExterno);
                    if (!servico.getBarbearia().getId().equals(barbearia.getId())) {
                        throw new EntidadeNaoPerteceABarbeariaException(Constants.Entity.SERVICO, Constants.Attribute.ID, servicoIdExterno.toString());
                    }

                    return servico;
                })
                .collect(Collectors.toSet());
    }

    private Set<Barbeiro> buscarBarbeirosDaBarbearia(List<UUID> barbeirosIdsExterno, Barbearia barbearia) {
        return barbeirosIdsExterno.stream()
                .map(barbeiroIdExterno -> {
                    var barbeiro = barbeiroService.buscarPorIdExterno(barbeiroIdExterno);
                    if (!barbeiro.getBarbearia().getId().equals(barbearia.getId())) {
                        throw new EntidadeNaoPerteceABarbeariaException(Constants.Entity.BARBEIRO, Constants.Attribute.ID_EXTERNO, barbeiroIdExterno.toString());
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
