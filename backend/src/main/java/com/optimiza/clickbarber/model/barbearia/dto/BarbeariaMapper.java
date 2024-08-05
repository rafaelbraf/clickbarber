package com.optimiza.clickbarber.model.barbearia.dto;

import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import com.optimiza.clickbarber.model.servico.dto.ServicoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class BarbeariaMapper {

    private final BarbeiroMapper barbeiroMapper;
    private final ServicoMapper servicoMapper;

    @Autowired
    public BarbeariaMapper(BarbeiroMapper barbeiroMapper, ServicoMapper servicoMapper) {
        this.barbeiroMapper = barbeiroMapper;
        this.servicoMapper = servicoMapper;
    }

    public BarbeariaDto toDto(Barbearia barbearia) {
        if (isNull(barbearia)) return null;

        var barbeirosDto = barbearia.getBarbeiros().stream()
                .map(barbeiroMapper::toDto)
                .toList();

        var servicosDto = barbearia.getServicos().stream()
                .map(servicoMapper::toDto)
                .toList();

        return BarbeariaDto.builder()
                .idExterno(barbearia.getIdExterno())
                .nome(barbearia.getNome())
                .email(barbearia.getUsuario().getEmail())
                .cnpj(barbearia.getCnpj())
                .telefone(barbearia.getTelefone())
                .endereco(barbearia.getEndereco())
                .servicos(servicosDto)
                .barbeiros(barbeirosDto)
                .build();
    }

    public Barbearia toEntity(BarbeariaCadastroDto barbeariaCadastro) {
        if (isNull(barbeariaCadastro)) return null;

        return Barbearia.builder()
                .nome(barbeariaCadastro.getNome())
                .cnpj(barbeariaCadastro.getCnpj())
                .endereco(barbeariaCadastro.getEndereco())
                .telefone(barbeariaCadastro.getTelefone())
                .usuario(barbeariaCadastro.getUsuario())
                .build();
    }

    public Barbearia toEntity(BarbeariaDto barbearia) {
        if (isNull(barbearia)) return null;

        return Barbearia.builder()
                .nome(barbearia.getNome())
                .cnpj(barbearia.getCnpj())
                .endereco(barbearia.getEndereco())
                .telefone(barbearia.getTelefone())
                .build();
    }

    public BarbeariaRespostaDto toRespostaDto(Barbearia barbearia) {
        return BarbeariaRespostaDto.builder()
                .nome(barbearia.getNome())
                .cnpj(barbearia.getCnpj())
                .endereco(barbearia.getEndereco())
                .telefone(barbearia.getTelefone())
                .idExterno(barbearia.getIdExterno())
                .role(barbearia.getUsuario().getRole())
                .build();
    }

}
