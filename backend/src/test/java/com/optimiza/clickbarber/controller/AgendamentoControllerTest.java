package com.optimiza.clickbarber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimiza.clickbarber.model.agendamento.dto.AgendamentoAtualizarDto;
import com.optimiza.clickbarber.model.agendamento.dto.AgendamentoCadastroDto;
import com.optimiza.clickbarber.service.AgendamentoService;
import com.optimiza.clickbarber.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AgendamentoController.class, useDefaultFilters = false)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService agendamentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long agendamentoId;
    private UUID barbeariaIdExterno;
    private UUID clienteIdExterno;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AgendamentoController(agendamentoService)).build();

        agendamentoId = 1L;
        barbeariaIdExterno = UUID.randomUUID();
        clienteIdExterno = UUID.randomUUID();
    }

    @Test
    void testBuscarAgendamentoPorId() throws Exception {
        var barbearia = montarBarbeariaDto(barbeariaIdExterno);
        var servico = montarServico();
        var barbeiro = montarBarbeiroAgendamentoDto();
        var cliente = montarClienteDto(clienteIdExterno);
        var agendamentoEncontrado = montarAgendamentoDto(agendamentoId, barbearia, cliente, servico, barbeiro);
        when(agendamentoService.buscarPorId(anyLong())).thenReturn(agendamentoEncontrado);

        mockMvc.perform(get("/agendamentos/" + agendamentoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constants.Success.AGENDAMENTO_ENCONTRADO_COM_SUCESSO))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.id").value(agendamentoId.toString()))
                .andExpect(jsonPath("$.result.tempoDuracaoEmMinutos").value(45))
                .andExpect(jsonPath("$.result.cliente.nome").value("Cliente Teste"))
                .andExpect(jsonPath("$.result.barbearia.nome").value("Barbearia Teste"))
                .andExpect(jsonPath("$.result.servicos.[0].nome").value("Serviço Teste"))
                .andExpect(jsonPath("$.result.barbeiros.[0].nome").value("Barbeiro Teste"));
    }

    @Test
    void testBuscarAgendamentosPeloIdExternoBarbearia() throws Exception {
        var agendamento = montarAgendamentoRespostaDto(1L, ZonedDateTime.now(), new BigDecimal("50.0"), barbeariaIdExterno, clienteIdExterno);
        when(agendamentoService.buscarPorIdExternoBarbearia(any(UUID.class))).thenReturn(List.of(agendamento));

        mockMvc.perform(get("/agendamentos/barbearia/{idExternoBarbearia}", barbeariaIdExterno))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Agendamentos encontrados para Barbearia com id " + barbeariaIdExterno))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result[0].id").value(1L))
                .andExpect(jsonPath("$.result[0].dataHora").value(notNullValue()))
                .andExpect(jsonPath("$.result[0].valorTotal").value("50.0"))
                .andExpect(jsonPath("$.result[0].cliente.nome").value("Cliente Teste"))
                .andExpect(jsonPath("$.result[0].barbearia.nome").value("Barbearia Teste"))
                .andExpect(jsonPath("$.result[0].barbeiros[0].nome").value("Barbeiro Teste"))
                .andExpect(jsonPath("$.result[0].servicos[0].nome").value("Serviço Teste"));
    }

    @Test
    void testBuscarAgendamentoComInformacoesReduzidasPeloIdExternoDaBarbearia() throws Exception {
        var agendamentoReduzido1 = montarAgendamentoReduzidoDto();
        when(agendamentoService.buscarReduzidoPorIdExternoBarberia(any(UUID.class))).thenReturn(List.of(agendamentoReduzido1));

        mockMvc.perform(get("/agendamentos/barbearia/{idExternoBarbearia}/reduzido", barbeariaIdExterno))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Agendamentos encontrados para Barbearia com id " + barbeariaIdExterno))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result[0].idExterno").value(notNullValue()))
                .andExpect(jsonPath("$.result[0].nomeCliente").value("Nome Cliente"))
                .andExpect(jsonPath("$.result[0].dataHoraInicio").value(notNullValue()))
                .andExpect(jsonPath("$.result[0].dataHoraFim").value(notNullValue()))
                .andExpect(jsonPath("$.result[0].servicos[0]").value("Serviço 1"));
    }

    @Test
    void testCadastrarAgendamento() throws Exception {
        var dataHora = ZonedDateTime.now();
        var valorTotal = new BigDecimal("75.50");
        var tempoDuracaoEmMinutos = 45;

        var agendamentoCadastro = montarAgendamentoCadastroDto(valorTotal, tempoDuracaoEmMinutos, dataHora);

        var agendamentoDto = montarAgendamentoDto(agendamentoId, dataHora, tempoDuracaoEmMinutos, valorTotal, barbeariaIdExterno, clienteIdExterno);

        when(agendamentoService.cadastrar(any(AgendamentoCadastroDto.class))).thenReturn(agendamentoDto);

        mockMvc.perform(post("/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agendamentoCadastro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.Success.AGENDAMENTO_CADASTRADO_COM_SUCESSO))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.id").value(agendamentoId.toString()))
                .andExpect(jsonPath("$.result.tempoDuracaoEmMinutos").value(tempoDuracaoEmMinutos))
                .andExpect(jsonPath("$.result.barbearia.nome").value("Barbearia Teste"));
    }

    @Test
    void testAtualizarAgendamento() throws Exception {
        var dataHora = ZonedDateTime.now();
        var tempoDuracaoEmMinutos = 45;
        var valorTotal = new BigDecimal("75.70");

        var agendamentoAtualizado = montarAgendamentoDto(
                agendamentoId, dataHora, 30, new BigDecimal("40"), barbeariaIdExterno, clienteIdExterno);
        when(agendamentoService.atualizar(any(AgendamentoAtualizarDto.class))).thenReturn(agendamentoAtualizado);

        var agendamentoParaAtualizar = montarAgendamentoAtualizarDto(agendamentoId, dataHora, valorTotal, tempoDuracaoEmMinutos);

        mockMvc.perform(put("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agendamentoParaAtualizar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.AGENDAMENTO_ATUALIZADO_COM_SUCESSO))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.id").value(agendamentoId.toString()))
                .andExpect(jsonPath("$.result.tempoDuracaoEmMinutos").value(30))
                .andExpect(jsonPath("$.result.valorTotal").value("40"));
    }

    @Test
    void testDeletarAgendamentoPorId() throws Exception {
        doNothing().when(agendamentoService).deletarPorId(agendamentoId);

        mockMvc.perform(delete("/agendamentos/" + agendamentoId))
                .andExpect(status().isNoContent());
    }

    private AgendamentoCadastroDto montarAgendamentoCadastroDto(BigDecimal valorTotal, Integer tempoDuracaoEmMinutos, ZonedDateTime dataHora) {
        return AgendamentoCadastroDto.builder()
            .valorTotal(valorTotal)
            .tempoDuracaoEmMinutos(tempoDuracaoEmMinutos)
            .dataHora(dataHora)
            .clienteId(1L)
            .barbeariaId(1L)
            .servicos(List.of(1L))
            .barbeiros(List.of(1L))
            .build();
    }

    private AgendamentoAtualizarDto montarAgendamentoAtualizarDto(Long agendamentoId, ZonedDateTime dataHora, BigDecimal valorTotal, Integer tempoDuracaoEmMinutos) {
        return AgendamentoAtualizarDto.builder()
                .id(agendamentoId)
                .dataHora(dataHora)
                .valorTotal(valorTotal)
                .tempoDuracaoEmMinutos(tempoDuracaoEmMinutos)
                .build();
    }
}
