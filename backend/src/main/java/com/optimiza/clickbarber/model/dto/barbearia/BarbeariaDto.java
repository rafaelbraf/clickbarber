package com.optimiza.clickbarber.model.dto.barbearia;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarbeariaDto {

    private Integer id;
    private String cnpj;
    private String nome;
    private String endereco;
    private String email;
    private String telefone;

}