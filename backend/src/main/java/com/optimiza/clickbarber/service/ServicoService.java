package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.Servico;
import com.optimiza.clickbarber.model.dto.barbearia.BarbeariaMapper;
import com.optimiza.clickbarber.model.dto.servico.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoCadastroDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoMapper;
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

    @Autowired
    public ServicoService(ServicoRepository servicoRepository, ServicoMapper servicoMapper, BarbeariaService barbeariaService, BarbeariaMapper barbeariaMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
        this.barbeariaService = barbeariaService;
    }

    public List<Servico> buscarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.SERVICO, Constants.Attribute.ID, id.toString()));
    }

    public List<ServicoDto> buscarPorIdExternoBarbearia(UUID idExternoBarbearia) {
        var servicos = servicoRepository.findByIdExternoBarbearia(idExternoBarbearia);

        return servicos.stream()
                .map(servicoMapper::toDto)
                .toList();
    }

    @Transactional
    public Servico cadastrar(ServicoCadastroDto servicoCadastroDto) {
        var barbeariaId = servicoCadastroDto.getBarbearia().getId();
        if (!barbeariaService.existePorId(barbeariaId)) {
            throw new ResourceNotFoundException(Constants.Entity.BARBEARIA, Constants.Attribute.ID, barbeariaId.toString());
        }
        var servico = servicoMapper.toEntity(servicoCadastroDto);
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
