package com.optimiza.clickbarber.model.barbeiro.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class BarbeiroAgendamentoDto {

    private UUID idExterno;
    private String nome;
    private String celular;
    private boolean ativo;

}
