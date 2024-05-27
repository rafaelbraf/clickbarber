package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.Servico;
import com.optimiza.clickbarber.model.dto.barbearia.BarbeariaMapper;
import com.optimiza.clickbarber.model.dto.servico.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoMapper;
import com.optimiza.clickbarber.repository.ServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;
    private final BarbeariaService barbeariaService;
    private final BarbeariaMapper barbeariaMapper;

    @Autowired
    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper, BarbeariaService barbeariaService, BarbeariaMapper barbeariaMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
        this.barbeariaService = barbeariaService;
        this.barbeariaMapper = barbeariaMapper;
    }

    public List<Servico> buscarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(UUID id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Serviço", "id", id.toString()));
    }

    public List<Servico> buscarPorBarbeariaId(Integer barbeariaId) {
        return servicoRepository.findByBarbeariaId(barbeariaId);
    }

    @Transactional
    public Servico cadastrar(ServicoDto servicoDto) {
        barbeariaService.buscarPorId(servicoDto.getBarbearia().getId());
        var servico = servicoMapper.toEntity(servicoDto);

        return servicoRepository.save(servico);
    }

    public Servico atualizar(ServicoAtualizarDto servicoAtualizar) {
        var servico = servicoRepository.findById(servicoAtualizar.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Servico", "id", servicoAtualizar.getId().toString()));

        servico.setNome(servicoAtualizar.getNome());
        servico.setAtivo(servicoAtualizar.isAtivo());
        servico.setPreco(servicoAtualizar.getPreco());
        servico.setTempoDuracaoEmMinutos(servicoAtualizar.getTempoDuracaoEmMinutos());

        return servicoRepository.save(servico);
    }

    public void deletarPorId(UUID id) {
        servicoRepository.deleteById(id);
    }
}
