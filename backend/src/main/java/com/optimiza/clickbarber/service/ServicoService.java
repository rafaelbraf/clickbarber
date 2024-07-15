package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaMapper;
import com.optimiza.clickbarber.model.servico.dto.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoCadastroDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoMapper;
import com.optimiza.clickbarber.repository.ServicoRepository;
import com.optimiza.clickbarber.utils.Constants;
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

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.SERVICO, Constants.Attribute.ID, id.toString()));
    }

    public Servico buscarPorIdExterno(UUID idExterno) {
        return servicoRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Constants.Entity.SERVICO, Constants.Attribute.ID_EXTERNO, idExterno.toString()));
    }

    public List<ServicoDto> buscarPorIdExternoBarbearia(UUID idExternoBarbearia) {
        var servicos = servicoRepository.findByIdExternoBarbearia(idExternoBarbearia);

        return servicos.stream()
                .map(servicoMapper::toDto)
                .toList();
    }

    @Transactional
    public Servico cadastrar(ServicoCadastroDto servicoCadastroDto) {
        var servico = servicoMapper.toEntity(servicoCadastroDto);
        var barbearia = barbeariaService.buscarPorIdExterno(servicoCadastroDto.getIdExternoBarbearia());
        servico.setBarbearia(barbearia);

        return servicoRepository.save(servico);
    }

    public Servico atualizar(ServicoAtualizarDto servicoAtualizar) {
        var servico = buscarPorId(servicoAtualizar.getId());
        servico.setNome(servicoAtualizar.getNome());
        servico.setAtivo(servicoAtualizar.isAtivo());
        servico.setPreco(servicoAtualizar.getPreco());
        servico.setTempoDuracaoEmMinutos(servicoAtualizar.getTempoDuracaoEmMinutos());
        return servicoRepository.save(servico);
    }

    public void deletarPorId(Long id) {
        servicoRepository.deleteById(id);
    }
}
