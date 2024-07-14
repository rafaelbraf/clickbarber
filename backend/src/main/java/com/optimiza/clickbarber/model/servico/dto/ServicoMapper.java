package com.optimiza.clickbarber.model.servico.dto;

import com.optimiza.clickbarber.model.servico.Servico;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ServicoMapper {

    public Servico toEntity(ServicoDto servicoDto) {
        return Servico.builder()
                .nome(servicoDto.getNome())
                .preco(servicoDto.getPreco())
                .tempoDuracaoEmMinutos(servicoDto.getTempoDuracaoEmMinutos())
                .ativo(servicoDto.isAtivo())
                .build();
    }

    public ServicoDto toDto(Servico servico) {
        return ServicoDto.builder()
                .idExterno(servico.getIdExterno())
                .nome(servico.getNome())
                .preco(servico.getPreco())
                .tempoDuracaoEmMinutos(servico.getTempoDuracaoEmMinutos())
                .ativo(servico.isAtivo())
                .build();
    }

    public Set<ServicoDto> toSetDto(Set<Servico> servicos) {
        return servicos.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

    public Servico toEntity(ServicoCadastroDto servicoCadastroDto) {
        return Servico.builder()
                .nome(servicoCadastroDto.getNome())
                .preco(servicoCadastroDto.getPreco())
                .tempoDuracaoEmMinutos(servicoCadastroDto.getTempoDuracaoEmMinutos())
                .ativo(servicoCadastroDto.isAtivo())
                .build();
    }

}
