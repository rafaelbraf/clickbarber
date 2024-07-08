package com.optimiza.clickbarber.model.barbeiro.dto;

import com.optimiza.clickbarber.model.barbeiro.Barbeiro;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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
                .id(barbeiro.getId())
                .nome(barbeiro.getNome())
                .cpf(barbeiro.getCpf())
                .celular(barbeiro.getCelular())
                .ativo(barbeiro.isAtivo())
                .admin(barbeiro.isAdmin())
                .build();
    }

    public Set<BarbeiroAgendamentoDto> toSetAgendamentoDto(Set<Barbeiro> barbeiros) {
        var barbeirosDto = new HashSet<BarbeiroAgendamentoDto>();
        for (var barbeiro : barbeiros) {
            var barbeiroDto = toAgendamentoDto(barbeiro);
            barbeirosDto.add(barbeiroDto);
        }
        return barbeirosDto;
    }

}
