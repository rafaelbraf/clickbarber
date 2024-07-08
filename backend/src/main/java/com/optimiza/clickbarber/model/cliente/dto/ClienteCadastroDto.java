package com.optimiza.clickbarber.model.cliente.dto;

import com.optimiza.clickbarber.model.usuario.Usuario;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ClienteCadastroDto {

    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String celular;
    private Usuario usuario;

}
