package com.optimiza.clickbarber.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimiza.clickbarber.config.JwtUtil;
import com.optimiza.clickbarber.model.autenticacao.RespostaLogin;
import com.optimiza.clickbarber.model.Role;
import com.optimiza.clickbarber.model.usuario.Usuario;
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaCadastroDto;
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroCadastroDto;
import com.optimiza.clickbarber.model.barbeiro.dto.BarbeiroDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteCadastroDto;
import com.optimiza.clickbarber.model.cliente.dto.ClienteDto;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioCadastrarDto;
import com.optimiza.clickbarber.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.*;

@Service
public class AutenticacaoService {

    private final BarbeariaService barbeariaService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final ClienteService clienteService;
    private final BarbeiroService barbeiroService;

    @Autowired
    public AutenticacaoService(BarbeariaService barbeariaService, JwtUtil jwtUtil, UsuarioService usuarioService, ObjectMapper objectMapper, ClienteService clienteService, BarbeiroService barbeiroService) {
        this.barbeariaService = barbeariaService;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
        this.clienteService = clienteService;
        this.barbeiroService = barbeiroService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public RespostaLogin login(LoginRequestDto loginRequest) {
        requireNonNull(loginRequest.getEmail(), Constants.Error.EMAIL_NAO_PODE_SER_NULO);
        requireNonNull(loginRequest.getSenha(), Constants.Error.SENHA_NAO_PODE_SER_NULA);

        var usuario = usuarioService.buscarPorEmail(loginRequest.getEmail());
        if (!isSenhaValida(loginRequest.getSenha(), usuario.getSenha())) {
            return RespostaLogin.unauthorized();
        }

        var acoesRole = Map.of(
                Role.CLIENTE, clienteService::buscarPorUsuarioId,
                Role.BARBEARIA, barbeariaService::buscarPorUsuarioIdLogin,
                Role.BARBEIRO, (Function<Long, Object>) barbeiroService::buscarPorUsuarioId
        );

        var acaoRole = acoesRole.get(usuario.getRole());
        if (nonNull(acaoRole)) {
            var entity = acaoRole.apply(usuario.getId());
            return RespostaLogin.authorized(entity, gerarToken(usuario.getEmail()));
        }

        return RespostaLogin.unauthorized();
    }

    @Transactional
    public Object cadastrarUsuario(UsuarioCadastrarDto usuarioRegistrar) {
        var usuarioCadastrado = usuarioService.cadastrarUsuario(usuarioRegistrar);
        return cadastrarObjeto(usuarioRegistrar, usuarioCadastrado);
    }

    private String gerarToken(String email) {
        return jwtUtil.gerarToken(email);
    }

    private boolean isSenhaValida(String senha, String senhaCadastrada) {
        return bCryptPasswordEncoder.matches(senha, senhaCadastrada);
    }

    private Object cadastrarObjeto(UsuarioCadastrarDto usuarioRegistrar, Usuario usuarioCadastrado) {
        if (isNull(usuarioCadastrado)) return null;

        var data = usuarioRegistrar.getData();
        var role = usuarioRegistrar.getRole();

        if (data instanceof Map) {
            Map<String, Object> dataMap = (Map<String, Object>) data;
            switch (role) {
                case Role.BARBEARIA -> {
                    return cadastrarBarbearia(dataMap, usuarioCadastrado);
                }
                case Role.CLIENTE -> {
                    return cadastrarCliente(dataMap, usuarioCadastrado);
                }
                case Role.BARBEIRO -> {
                    return cadastrarBarbeiro(dataMap, usuarioCadastrado);
                }
            }
        }

        return null;
    }

    private BarbeariaDto cadastrarBarbearia(Map<String, Object> dataMap, Usuario usuarioCadastrado) {
        var barbeariaCadastro = objectMapper.convertValue(dataMap, BarbeariaCadastroDto.class);
        barbeariaCadastro.setUsuario(usuarioCadastrado);
        return barbeariaService.cadastrar(barbeariaCadastro);
    }

    private ClienteDto cadastrarCliente(Map<String, Object> dataMap, Usuario usuarioCadastrado) {
        var clienteCadastro = objectMapper.convertValue(dataMap, ClienteCadastroDto.class);
        clienteCadastro.setUsuario(usuarioCadastrado);
        return clienteService.cadastrar(clienteCadastro);
    }

    private BarbeiroDto cadastrarBarbeiro(Map<String, Object> dataMap, Usuario usuarioCadastrado) {
        var barbeiroCadastro = objectMapper.convertValue(dataMap, BarbeiroCadastroDto.class);
        barbeiroCadastro.setUsuario(usuarioCadastrado);
        return barbeiroService.cadastrar(barbeiroCadastro);
    }

}
