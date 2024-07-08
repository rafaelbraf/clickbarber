package com.optimiza.clickbarber.model.barbeiro.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class BarbeiroDto {

    private UUID idExterno;
    private String cpf;
    private String nome;
    private String celular;
    private boolean admin;
    private boolean ativo;

}
