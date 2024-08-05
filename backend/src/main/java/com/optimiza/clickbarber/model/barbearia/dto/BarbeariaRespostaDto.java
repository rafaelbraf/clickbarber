package com.optimiza.clickbarber.model.barbearia.dto;

import com.optimiza.clickbarber.model.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarbeariaRespostaDto {

    private UUID idExterno;
    private String cnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private Role role;

}
