package com.optimiza.clickbarber.model.autenticacao.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class LoginRequestDto {

    private String email;
    private String senha;

}
