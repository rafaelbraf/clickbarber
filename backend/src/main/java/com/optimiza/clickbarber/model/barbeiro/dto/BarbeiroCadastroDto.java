package com.optimiza.clickbarber.model.barbeiro.dto;

import com.optimiza.clickbarber.model.usuario.Usuario;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class BarbeiroCadastroDto {

    private String nome;
    private String cpf;
    private String celular;
    private boolean admin;
    private boolean ativo;
    private UUID idExternoBarbearia;
    private Usuario usuario;

}
