package com.optimiza.clickbarber.service;

import com.optimiza.clickbarber.config.JwtUtil;
import com.optimiza.clickbarber.exception.ResourceNotFoundException;
import com.optimiza.clickbarber.model.Role;
import com.optimiza.clickbarber.model.dto.barbearia.BarbeariaRespostaLoginDto;
import com.optimiza.clickbarber.model.dto.barbeiro.BarbeiroDto;
import com.optimiza.clickbarber.model.dto.cliente.ClienteDto;
import com.optimiza.clickbarber.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static com.optimiza.clickbarber.utils.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private BarbeariaService barbeariaService;

    @Mock
    private BarbeiroService barbeiroService;

    @Mock
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Long usuarioId;
    private String email;
    private String senha;
    private UUID barbeariaIdExterno;
    private UUID clienteIdExterno;
    private UUID barbeiroIdExterno;

    @BeforeEach
    void setup() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();

        usuarioId = 1L;
        email = "teste@mail.com";
        senha = "teste";
        barbeariaIdExterno = UUID.randomUUID();
        clienteIdExterno = UUID.randomUUID();
    }

    @Test
    void testLoginCliente() {
        var usuario = montarUsuario(Role.CLIENTE, senha);
        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuario);

        var cliente = montarClienteDto(clienteIdExterno);
        when(clienteService.buscarPorUsuarioId(anyLong())).thenReturn(cliente);

        when(jwtUtil.gerarToken(anyString())).thenReturn("token_cliente");

        var loginRequest = montarLoginRequestDto();
        var resposta = autenticacaoService.login(loginRequest);

        assertNotNull(resposta);
        assertTrue(resposta.isSuccess());
        assertEquals(Constants.Success.LOGIN_REALIZADO_COM_SUCESSO, resposta.getMessage());
        assertEquals("token_cliente", resposta.getAccessToken());
        assertNotNull(resposta.getResult());
        assertInstanceOf(ClienteDto.class, resposta.getResult());
    }

    @Test
    void testLoginBarbearia() {
        var usuario = montarUsuario(Role.BARBEARIA, senha);
        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuario);

        var barbearia = montarBarbeariaRespostaLoginDto(barbeariaIdExterno);
        when(barbeariaService.buscarPorUsuarioIdLogin(anyLong())).thenReturn(barbearia);

        when(jwtUtil.gerarToken(anyString())).thenReturn("token_barbearia");

        var loginRequest = montarLoginRequestDto();
        var resposta = autenticacaoService.login(loginRequest);

        assertNotNull(resposta);
        assertTrue(resposta.isSuccess());
        assertEquals(Constants.Success.LOGIN_REALIZADO_COM_SUCESSO, resposta.getMessage());
        assertEquals("token_barbearia", resposta.getAccessToken());
        assertNotNull(resposta.getResult());
        assertInstanceOf(BarbeariaRespostaLoginDto.class, resposta.getResult());
    }

    @Test
    void testLoginBarbeiro() {
        var usuario = montarUsuario(Role.BARBEIRO, senha);
        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuario);

        var barbeiro = montarBarbeiroDto(barbeiroIdExterno);
        when(barbeiroService.buscarPorUsuarioId(anyLong())).thenReturn(barbeiro);

        when(jwtUtil.gerarToken(anyString())).thenReturn("token_barbeiro");

        var loginRequest = montarLoginRequestDto();
        var resposta = autenticacaoService.login(loginRequest);

        assertNotNull(resposta);
        assertTrue(resposta.isSuccess());
        assertEquals(Constants.Success.LOGIN_REALIZADO_COM_SUCESSO, resposta.getMessage());
        assertEquals("token_barbeiro", resposta.getAccessToken());
        assertNotNull(resposta.getResult());
        assertInstanceOf(BarbeiroDto.class, resposta.getResult());
    }

    @Test
    void testLoginNaoAutorizado_SenhaIncorreta() {
        var usuario = montarUsuario(Role.BARBEARIA, "teste123");
        when(usuarioService.buscarPorEmail(anyString())).thenReturn(usuario);

        var loginRequest = montarLoginRequestDto();
        var resposta = autenticacaoService.login(loginRequest);

        assertNotNull(resposta);
        assertFalse(resposta.isSuccess());
        assertEquals(Constants.Error.EMAIL_OU_SENHA_INCORRETA, resposta.getMessage());
        assertNull(resposta.getResult());
        assertNull(resposta.getAccessToken());
    }

    @Test
    void testLoginNaoAutorizado_EmailNaoEncontrado() {
        when(usuarioService.buscarPorEmail(anyString())).thenThrow(new ResourceNotFoundException(Constants.Entity.USUARIO, Constants.Attribute.EMAIL, email));

        var loginRequest = montarLoginRequestDto();
        assertThrows(ResourceNotFoundException.class, () -> autenticacaoService.login(loginRequest));
    }

}
