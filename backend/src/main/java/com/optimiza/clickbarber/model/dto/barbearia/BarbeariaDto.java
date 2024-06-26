package com.optimiza.clickbarber.model.dto.barbearia;

import com.optimiza.clickbarber.model.Servico;
import com.optimiza.clickbarber.model.dto.barbeiro.BarbeiroDto;
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
    private String endereco;
    private String telefone;
    private List<Servico> servicos;
    private List<BarbeiroDto> barbeiros;

}
