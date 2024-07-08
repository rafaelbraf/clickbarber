package com.optimiza.clickbarber.model.barbeiro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BarbeiroAgendamentoDto {

    private Long id;
    private String cpf;
    private String nome;
    private String celular;
    private boolean admin;
    private boolean ativo;

}
