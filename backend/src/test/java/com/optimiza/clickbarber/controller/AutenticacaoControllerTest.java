package com.optimiza.clickbarber.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimiza.clickbarber.model.autenticacao.RespostaLogin;
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioCadastrarDto;
import com.optimiza.clickbarber.service.AutenticacaoService;
import com.optimiza.clickbarber.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AutenticacaoController.class, useDefaultFilters = false)
class AutenticacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AutenticacaoService autenticacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID barbeariaIdExterno;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AutenticacaoController(autenticacaoService)).build();

        barbeariaIdExterno = UUID.randomUUID();
    }

    @Test
    void testLogin() throws Exception {
        var token = "token_teste";
        var barbearia = montarBarbeariaDto(barbeariaIdExterno);
        var respostaLogin = RespostaLogin.authorized(barbearia, token);
        when(autenticacaoService.login(any(LoginRequestDto.class), any(HttpServletRequest.class))).thenReturn(respostaLogin);

        var loginRequest = montarLoginRequestDto();
        var loginRequestJsonString = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/auth/login")
                .content(loginRequestJsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constants.Success.LOGIN_REALIZADO_COM_SUCESSO))
                .andExpect(jsonPath("$.accessToken").value(token))
                .andExpect(jsonPath("$.result.idExterno").value(barbeariaIdExterno.toString()))
                .andExpect(jsonPath("$.result.nome").value("Barbearia Teste"));
    }

    @Test
    void testCadastrarUsuario() throws Exception {
        var barbeariaDto = montarBarbeariaDto(barbeariaIdExterno);
        when(autenticacaoService.cadastrarUsuario(any(UsuarioCadastrarDto.class))).thenReturn(barbeariaDto);

        var barbearia = montarBarbearia();
        var usuarioParaCadastro = montarUsuarioBarbearia(barbearia);
        var usuarioParaCadastroJsonString = objectMapper.writeValueAsString(usuarioParaCadastro);

        mockMvc.perform(post("/auth/cadastrar")
                .content(usuarioParaCadastroJsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(Constants.Success.USUARIO_CADASTRADO_COM_SUCESSO))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result.idExterno").value(barbeariaIdExterno.toString()));
    }

}
