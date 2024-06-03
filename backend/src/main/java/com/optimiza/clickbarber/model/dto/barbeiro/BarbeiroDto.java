package com.optimiza.clickbarber.model.dto.barbeiro;

import com.optimiza.clickbarber.model.Barbearia;
import com.optimiza.clickbarber.model.dto.barbearia.BarbeariaDto;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class BarbeiroDto {

    private Integer id;
    private String cpf;
    private String nome;
    private String celular;
    private boolean admin;
    private boolean ativo;
    private BarbeariaDto barbearia;

}
