package com.optimiza.clickbarber.model.barbearia.dto;

import com.optimiza.clickbarber.model.barbearia.Barbearia;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class BarbeariaMapper {

    private final BarbeiroMapper barbeiroMapper;

    @Autowired
    public BarbeariaMapper(BarbeiroMapper barbeiroMapper) {
        this.barbeiroMapper = barbeiroMapper;
    }

    public BarbeariaDto toDto(Barbearia barbearia) {
        if (isNull(barbearia)) return null;

        var barbeirosDto = barbearia.getBarbeiros().stream()
                .map(barbeiroMapper::toDto)
                .toList();

        return BarbeariaDto.builder()
                .idExterno(barbearia.getIdExterno())
                .nome(barbearia.getNome())
                .cnpj(barbearia.getCnpj())
                .telefone(barbearia.getTelefone())
                .endereco(barbearia.getEndereco())
                .servicos(barbearia.getServicos())
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

    public BarbeariaRespostaLoginDto toRespostaLoginDto(Barbearia barbearia) {
        return BarbeariaRespostaLoginDto.builder()
                .nome(barbearia.getNome())
                .cnpj(barbearia.getCnpj())
                .endereco(barbearia.getEndereco())
                .telefone(barbearia.getTelefone())
                .idExterno(barbearia.getIdExterno())
                .build();
    }

}
