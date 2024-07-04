package com.optimiza.clickbarber.model.dto.servico;

import com.optimiza.clickbarber.model.Servico;
import org.springframework.stereotype.Component;

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

    public Servico toEntity(ServicoCadastroDto servicoCadastroDto) {
        return Servico.builder()
                .nome(servicoCadastroDto.getNome())
                .preco(servicoCadastroDto.getPreco())
                .tempoDuracaoEmMinutos(servicoCadastroDto.getTempoDuracaoEmMinutos())
                .ativo(servicoCadastroDto.isAtivo())
                .barbearia(servicoCadastroDto.getBarbearia())
                .build();
    }

}
