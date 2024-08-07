package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroAtualizarDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroCadastroDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import com.optimiza.clickbarber.repository.BarbeiroRepository;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BarbeiroService {

    private final BarbeiroRepository barbeiroRepository;
    private final BarbeariaService barbeariaService;
    private final BarbeiroMapper barbeiroMapper;
    private final UsuarioService usuarioService;

    public BarbeiroService(BarbeiroRepository barbeiroRepository, BarbeariaService barbeariaService, BarbeiroMapper barbeiroMapper, UsuarioService usuarioService) {
        this.barbeiroRepository = barbeiroRepository;
        this.barbeariaService = barbeariaService;
        this.barbeiroMapper = barbeiroMapper;
        this.usuarioService = usuarioService;
    }

    public Barbeiro buscarPorId(Long id) {
        return barbeiroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEIRO, Constants.Attribute.ID, id.toString()));
    }

    public Barbeiro buscarPorIdExterno(UUID idExterno) {
        return barbeiroRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Constants.Entity.BARBEIRO, Constants.Attribute.ID_EXTERNO, idExterno.toString()));
    }

    public List<BarbeiroDto> buscarPorIdExternoBarbearia(UUID idExternoBarbearia) {
        return barbeiroRepository.findByIdExternoBarbearia(idExternoBarbearia).stream()
                .map(barbeiroMapper::toDto)
                .toList();
    }

    public BarbeiroDto buscarPorUsuarioId(Long usuarioId) {
        var barbeiro = barbeiroRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEIRO, Constants.Attribute.USUARIO_ID, usuarioId.toString()));
        return barbeiroMapper.toDto(barbeiro);
    }

    public BarbeiroDto cadastrar(BarbeiroCadastroDto barbeiroCadastroDto) {
        var barbeiro = barbeiroMapper.toEntity(barbeiroCadastroDto);
        var barbearia = barbeariaService.buscarPorIdExterno(barbeiroCadastroDto.getIdExternoBarbearia());
        barbeiro.setBarbearia(barbearia);

        var barbeiroCadastrado = barbeiroRepository.save(barbeiro);

        return barbeiroMapper.toDto(barbeiroCadastrado);
    }

    public Barbeiro atualizar(BarbeiroAtualizarDto barbeiroAtualizar) {
        var barbeiroExistente = buscarPorId(barbeiroAtualizar.getId());
        barbeiroExistente.setNome(barbeiroAtualizar.getNome());
        barbeiroExistente.setAtivo(barbeiroAtualizar.isAtivo());
        barbeiroExistente.setAdmin(barbeiroAtualizar.isAdmin());
        barbeiroExistente.setCelular(barbeiroAtualizar.getCelular());
        return barbeiroRepository.save(barbeiroExistente);
    }

    @Transactional
    public void deletarPorIdExterno(UUID idExterno) {
        var barbeiro = barbeiroRepository.findByIdExterno(idExterno)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.Entity.BARBEIRO, Constants.Attribute.ID_EXTERNO, idExterno.toString()));
        usuarioService.deletarById(barbeiro.getUsuario().getId());
        barbeiroRepository.deleteById(barbeiro.getId());
    }

}
