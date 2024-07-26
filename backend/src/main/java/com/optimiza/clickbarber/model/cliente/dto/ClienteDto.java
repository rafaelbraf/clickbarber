package com.optimiza.clickbarber.model.cliente.dto;

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
public class ClienteDto {

    private UUID idExterno;
    private String nome;
    private LocalDate dataNascimento;
    private String celular;

}
