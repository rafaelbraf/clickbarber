package com.optimiza.clickbarber.model.barbearia.dto;

import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoDto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarbeariaDto {

    private UUID idExterno;
    private String cnpj;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private List<ServicoDto> servicos;
    private List<BarbeiroDto> barbeiros;

}
