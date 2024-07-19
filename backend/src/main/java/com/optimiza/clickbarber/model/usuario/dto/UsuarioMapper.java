package com.optimiza.clickbarber.model.usuario.dto;

import com.optimiza.clickbarber.model.usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioCadastrarDto usuarioRegistrar) {
        return new Usuario(
                null,
                null,
                usuarioRegistrar.getEmail(),
                usuarioRegistrar.getSenha(),
                usuarioRegistrar.getRole()
        );
    }

    public UsuarioAgendamentoDto toAgendamentoDto(Usuario usuario) {
        return UsuarioAgendamentoDto.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .build();
    }

}
