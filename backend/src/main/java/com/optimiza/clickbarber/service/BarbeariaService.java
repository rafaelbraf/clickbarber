package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaCadastroDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaMapper;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaRespostaLoginDto;
import com.optimiza.clickbarber.repository.BarbeariaRepository;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;
    private final BarbeariaMapper barbeariaMapper;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository, BarbeariaMapper barbeariaMapper) {
        this.barbeariaRepository = barbeariaRepository;
        this.barbeariaMapper = barbeariaMapper;
    }

    public boolean existePorId(Long id) {
        return barbeariaRepository.existsById(id);
    }

    public List<BarbeariaDto> buscarTodos(Pageable pageable) {
        List<Barbearia> barbeariasEncontradas;
        if (pageable.isUnpaged()) {
            barbeariasEncontradas = barbeariaRepository.findAll();
        } else {
            barbeariasEncontradas = barbeariaRepository.findAll(pageable).getContent();
        }

        var barbeariasEncontradasDto = new ArrayList<BarbeariaDto>();
        barbeariasEncontradas.forEach(barbearia -> {
            var barbeariaDto = barbeariaMapper.toDto(barbearia);
            barbeariasEncontradasDto.add(barbeariaDto);
        });

        return barbeariasEncontradasDto;
    }

    public Barbearia buscarPorId(Long id) {
        return barbeariaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEARIA, Constants.Attribute.ID, id.toString()));
    }

    public Barbearia buscarPorIdExterno(UUID idExterno) {
        return barbeariaRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEARIA, Constants.Attribute.ID_EXTERNO, idExterno.toString()));
    }

    public BarbeariaDto buscarDtoPorIdExteno(UUID idExterno) {
        var barbeariaEncontrada = buscarPorIdExterno(idExterno);
        return barbeariaMapper.toDto(barbeariaEncontrada);
    }

    public List<BarbeariaDto> buscarPorNome(String nome) {
        var barbeariasEncontradas = barbeariaRepository.findByNomeContainingIgnoreCase(nome);
        var barbeariasEncontradasDto = new ArrayList<BarbeariaDto>();

        barbeariasEncontradas.forEach(barbearia -> {
            var barbeariaDto = barbeariaMapper.toDto(barbearia);
            barbeariasEncontradasDto.add(barbeariaDto);
        });

        return barbeariasEncontradasDto;
    }

    public BarbeariaDto buscarPorUsuarioId(Long usuarioId) {
        var barbearia = barbeariaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEARIA, Constants.Attribute.USUARIO_ID, usuarioId.toString()));
        return barbeariaMapper.toDto(barbearia);
    }

    public BarbeariaRespostaLoginDto buscarPorUsuarioIdLogin(Long usuarioId) {
        var barbearia = barbeariaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEARIA, Constants.Attribute.USUARIO_ID, usuarioId.toString()));
        return barbeariaMapper.toRespostaLoginDto(barbearia);
    }

    public BarbeariaDto cadastrar(BarbeariaCadastroDto barbeariaCadastro) {
        var barbearia = barbeariaMapper.toEntity(barbeariaCadastro);
        var barbeariaCadastrada = barbeariaRepository.save(barbearia);
        return barbeariaMapper.toDto(barbeariaCadastrada);
    }
}
