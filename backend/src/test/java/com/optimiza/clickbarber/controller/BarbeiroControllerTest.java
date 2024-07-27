package com.optimiza.clickbarber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimiza.clickbarber.model.Role;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroAtualizarDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroCadastroDto;
import com.optimiza.clickbarber.service.BarbeiroService;
import com.optimiza.clickbarber.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BarbeiroController.class, useDefaultFilters = false)
class BarbeiroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BarbeiroService barbeiroService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID barbeariaIdExterno;
    private UUID barbeiroIdExterno;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BarbeiroController(barbeiroService)).build();

        barbeariaIdExterno = UUID.randomUUID();
        barbeiroIdExterno = UUID.randomUUID();
    }

    @Test
    void testBuscarBarbeiroPorId() throws Exception {
        when(barbeiroService.buscarPorId(anyLong())).thenReturn(montarBarbeiro());

        mockMvc.perform(get("/barbeiros/" + 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.BARBEIRO_ENCONTRADO_COM_SUCESSO))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.id").value(1))
                .andExpect(jsonPath("$.result.cpf").value("01345678910"))
                .andExpect(jsonPath("$.result.usuario.id").value(1))
                .andExpect(jsonPath("$.result.usuario.role").value(Role.BARBEIRO.toString()))
                .andExpect(jsonPath("$.result.barbearia.id").value(1))
                .andExpect(jsonPath("$.result.barbearia.nome").value("Barbearia Teste"));
    }

    @Test
    void testBuscarBarbeirosPorBarbeariaId() throws Exception {
        var barbeiroDto1 = montarBarbeiroDto(barbeiroIdExterno);
        var idExternoBarbeiroDto2 = UUID.randomUUID();
        var barbeiroDto2 = montarBarbeiroDto(idExternoBarbeiroDto2, "Barbeiro Teste 2");
        when(barbeiroService.buscarPorIdExternoBarbearia(any(UUID.class))).thenReturn(List.of(barbeiroDto1, barbeiroDto2));

        mockMvc.perform(get("/barbeiros/barbearia/" + barbeariaIdExterno))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.BARBEIROS_ENCONTRADOS_DA_BARBEARIA + barbeariaIdExterno))
                .andExpect(jsonPath("$.result.[0].idExterno").value(barbeiroIdExterno.toString()))
                .andExpect(jsonPath("$.result.[0].nome").value("Barbeiro Teste"))
                .andExpect(jsonPath("$.result.[1].idExterno").value(idExternoBarbeiroDto2.toString()))
                .andExpect(jsonPath("$.result.[1].nome").value("Barbeiro Teste 2"));
    }

    @Test
    void testCadastrarBarbeiro() throws Exception {
        var barbeiroCadastrado = montarBarbeiroDto(barbeiroIdExterno);
        when(barbeiroService.cadastrar(any(BarbeiroCadastroDto.class))).thenReturn(barbeiroCadastrado);

        var barbeiroCadastrar = montarBarbeiroCadastroDto();

        mockMvc.perform(post("/barbeiros")
                .content(objectMapper.writeValueAsString(barbeiroCadastrar))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.Success.BARBEIRO_CADASTRADO_COM_SUCESSO))
                .andExpect(jsonPath("$.result.nome").value("Barbeiro Teste"));
    }

    @Test
    void testAtualizarBarbeiro() throws Exception {
        var barbeiro = montarBarbeiro();
        barbeiro.setAtivo(false);
        barbeiro.setNome("Barbeiro Atualizado");
        when(barbeiroService.atualizar(any(BarbeiroAtualizarDto.class))).thenReturn(barbeiro);

        var barbeiroAtualizarDto = BarbeiroAtualizarDto.builder()
                .id(1L)
                .nome("Barbeiro Atualizado")
                .ativo(false)
                .admin(false)
                .celular("988888888")
                .build();

        mockMvc.perform(put("/barbeiros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(barbeiroAtualizarDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.BARBEIRO_ATUALIZADO_COM_SUCESSO))
                .andExpect(jsonPath("$.result.nome").value("Barbeiro Atualizado"))
                .andExpect(jsonPath("$.result.ativo").value(false));
    }

    @Test
    void testDeletarBarbeiro() throws Exception {
        doNothing().when(barbeiroService).deletarPorIdExterno(UUID.randomUUID());

        mockMvc.perform(delete("/barbeiros/" + 1L))
                .andExpect(status().isNoContent());
    }

}
