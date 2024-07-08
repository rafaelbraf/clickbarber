package com.optimiza.clickbarber.model.barbearia.dto;

import com.optimiza.clickbarber.model.usuario.Usuario;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class BarbeariaCadastroDto {

    private String cnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private Usuario usuario;

}
