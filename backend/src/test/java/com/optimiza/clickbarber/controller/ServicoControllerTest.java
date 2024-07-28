package com.optimiza.clickbarber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimiza.clickbarber.model.servico.Servico;
import com.optimiza.clickbarber.model.servico.dto.ServicoAtualizarDto;
import com.optimiza.clickbarber.model.servico.dto.ServicoCadastroDto;
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
import java.util.UUID;

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
    private UUID idExternoBarbearia;
    private UUID idExternoServico;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ServicoController(servicoService)).build();

        servicoId = 1L;
        servico = montarServico();
        idExternoBarbearia = UUID.randomUUID();
        idExternoServico = UUID.randomUUID();
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
    void testBuscarServicosPorIdExternoBarbearia() throws Exception {
        var servico1 = montarServicoDto(idExternoServico);

        var idExternoServico2 = UUID.randomUUID();
        var servico2 = montarServicoDto(idExternoServico2,"Serviço Teste 2");

        var servicosEncontrados = List.of(servico1, servico2);

        when(servicoService.buscarPorIdExternoBarbearia(any(UUID.class))).thenReturn(servicosEncontrados);

        mockMvc.perform(get("/servicos/barbearia/{idExternoBarbearia}", idExternoBarbearia))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.SERVICOS_ENCONTRADOS_DA_BARBEARIA + idExternoBarbearia))
                .andExpect(jsonPath("$.result.[0].idExterno").value(idExternoServico.toString()))
                .andExpect(jsonPath("$.result.[0].nome").value("Serviço Teste"))
                .andExpect(jsonPath("$.result.[1].idExterno").value(idExternoServico2.toString()))
                .andExpect(jsonPath("$.result.[1].nome").value("Serviço Teste 2"));
    }

    @Test
    void testCadastrarServico() throws Exception {
        when(servicoService.cadastrar(any(ServicoCadastroDto.class))).thenReturn(servico);

        var servicoParaCadastro = objectMapper.writeValueAsString(montarServicoDto(idExternoServico));

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
        when(servicoService.buscarPorIdExterno(any(UUID.class))).thenReturn(montarServico());
        doNothing().when(servicoService).deletarPorIdExterno(any(UUID.class));

        mockMvc.perform(delete("/servicos/" + idExternoServico))
                .andExpect(status().isNoContent());
    }

}
