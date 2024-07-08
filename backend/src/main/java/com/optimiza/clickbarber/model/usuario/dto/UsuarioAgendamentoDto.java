package com.optimiza.clickbarber.model.usuario.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAgendamentoDto {

    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String celular;

}
