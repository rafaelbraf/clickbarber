package com.optimiza.clickbarber.model.dto.cliente;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteAgendamentoDto {

    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String celular;

}
