package com.optimiza.clickbarber.model.barbeiro.dto;

import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaRespostaDto;
import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BarbeiroMapper {
    public Barbeiro toEntity(BarbeiroCadastroDto barbeiroCadastro) {
        return Barbeiro.builder()
                .nome(barbeiroCadastro.getNome())
                .cpf(barbeiroCadastro.getCpf())
                .celular(barbeiroCadastro.getCelular())
                .ativo(barbeiroCadastro.isAtivo())
                .admin(barbeiroCadastro.isAdmin())
                .usuario(barbeiroCadastro.getUsuario())
                .build();
    }

    public BarbeiroDto toDto(Barbeiro barbeiro) {
        return BarbeiroDto.builder()
                .idExterno(barbeiro.getIdExterno())
                .nome(barbeiro.getNome())
                .cpf(barbeiro.getCpf())
                .celular(barbeiro.getCelular())
                .admin(barbeiro.isAdmin())
                .ativo(barbeiro.isAtivo())
                .build();
    }

    public BarbeiroAgendamentoDto toAgendamentoDto(Barbeiro barbeiro) {
        return BarbeiroAgendamentoDto.builder()
                .idExterno(barbeiro.getIdExterno())
                .nome(barbeiro.getNome())
                .celular(barbeiro.getCelular())
                .ativo(barbeiro.isAtivo())
                .build();
    }

    public Set<BarbeiroAgendamentoDto> toSetAgendamentoDto(Set<Barbeiro> barbeiros) {
        return barbeiros.stream()
                .map(this::toAgendamentoDto)
                .collect(Collectors.toSet());
    }

    public BarbeiroRespostaDto toRespostaDto(Barbeiro barbeiro) {
        return new BarbeiroRespostaDto(
            barbeiro.getIdExterno(),
            barbeiro.getNome(),
            barbeiro.getCelular(),
            barbeiro.isAdmin()
        );
    }

}
