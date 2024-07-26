package com.optimiza.clickbarber.model.cliente.dto;

import com.optimiza.clickbarber.model.cliente.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteCadastroDto usuarioCadastro) {
        return new Cliente(
                usuarioCadastro.getNome(),
                usuarioCadastro.getDataNascimento(),
                usuarioCadastro.getCelular(),
                usuarioCadastro.getUsuario()
        );
    }

    public ClienteDto toDto(Cliente cliente) {
        return ClienteDto.builder()
                .idExterno(cliente.getIdExterno())
                .nome(cliente.getNome())
                .celular(cliente.getCelular())
                .dataNascimento(cliente.getDataNascimento())
                .build();
    }

    public ClienteAgendamentoDto toAgendamentoDto(Cliente cliente) {
        return ClienteAgendamentoDto.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .celular(cliente.getCelular())
                .build();
    }

}
