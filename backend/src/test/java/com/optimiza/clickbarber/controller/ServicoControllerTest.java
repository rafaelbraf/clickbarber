package com.optimiza.clickbarber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimiza.clickbarber.model.Servico;
import com.optimiza.clickbarber.model.dto.servico.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.dto.servico.ServicoDto;
import com.optimiza.clickbarber.service.ServicoService;
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

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ServicoController.class, useDefaultFilters = false)
class ServicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ServicoService servicoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long servicoId;
    private Servico servico;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ServicoController(servicoService)).build();

        servicoId = 1L;
        servico = montarServico();
    }

    @Test
    void testBuscarTodosOsServicos() throws Exception {
        var servico2 = montarServico("Serviço Teste 2");

        var servicos = List.of(servico, servico2);

        when(servicoService.buscarTodos()).thenReturn(servicos);

        mockMvc.perform(get("/servicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.SERVICOS_ENCONTRADOS))
                .andExpect(jsonPath("$.result.[0].nome").value("Serviço Teste"))
                .andExpect(jsonPath("$.result.[0].id").value(servicoId.toString()))
                .andExpect(jsonPath("$.result.[1].nome").value("Serviço Teste 2"));
    }

    @Test
    void testBuscarServicoPorId() throws Exception {
        when(servicoService.buscarPorId(anyLong())).thenReturn(servico);

        mockMvc.perform(get("/servicos/" + servicoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.SERVICO_ENCONTRADO_COM_SUCESSO))
                .andExpect(jsonPath("$.result.nome").value("Serviço Teste"));
    }

    @Test
    void testBuscarServicosPorBarbeariaId() throws Exception {
        var servico2 = montarServico("Serviço Teste 2");

        var servicosEncontrados = List.of(servico, servico2);

        when(servicoService.buscarPorBarbeariaId(anyInt())).thenReturn(servicosEncontrados);

        mockMvc.perform(get("/servicos/barbearia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.SERVICOS_ENCONTRADOS_DA_BARBEARIA + 1))
                .andExpect(jsonPath("$.result.[0].nome").value("Serviço Teste"))
                .andExpect(jsonPath("$.result.[0].id").value(servicoId.toString()))
                .andExpect(jsonPath("$.result.[1].nome").value("Serviço Teste 2"));
    }

    @Test
    void testCadastrarServico() throws Exception {
        when(servicoService.cadastrar(any(ServicoDto.class))).thenReturn(servico);

        var servicoParaCadastro = objectMapper.writeValueAsString(montarServicoDto());

        mockMvc.perform(post("/servicos")
                .content(servicoParaCadastro)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.Success.SERVICO_CADASTRADO_COM_SUCESSO));
    }

    @Test
    void testAtualizarServico() throws Exception {
        var servicoAtualizado = montarServico("Serviço Atualizar");
        when(servicoService.atualizar(any(ServicoAtualizarDto.class))).thenReturn(servicoAtualizado);

        var servicoParaAtualizar = objectMapper.writeValueAsString(montarServicoAtualizarDto());

        mockMvc.perform(put("/servicos")
                .content(servicoParaAtualizar)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.SERVICO_ATUALIZADO_COM_SUCESSO))
                .andExpect(jsonPath("$.result.id").value(servicoId.toString()))
                .andExpect(jsonPath("$.result.nome").value("Serviço Atualizar"))
                .andExpect(jsonPath("$.result.preco").value("50.0"));
    }

    @Test
    void testDeletarServicoPorId() throws Exception {
        doNothing().when(servicoService).deletarPorId(anyLong());

        mockMvc.perform(delete("/servicos/" + servicoId))
                .andExpect(status().isNoContent());
    }

}
