package com.optimiza.clickbarber.controller;

import com.optimiza.clickbarber.model.Resposta;
import com.optimiza.clickbarber.model.autenticacao.RespostaAutenticacao;
import com.optimiza.clickbarber.model.RespostaUtils;
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto;
import com.optimiza.clickbarber.model.usuario.dto.UsuarioCadastrarDto;
import com.optimiza.clickbarber.service.AutenticacaoService;
import com.optimiza.clickbarber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    @Autowired
    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<RespostaAutenticacao<Object>> login(@RequestBody LoginRequestDto loginRequest) {
        var respostaLogin = autenticacaoService.login(loginRequest);
        var resposta = RespostaUtils.authorized(respostaLogin.getMessage(), respostaLogin.getResult(), respostaLogin.getAccessToken());
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Resposta<Object>> cadastrarUsuario(@RequestBody UsuarioCadastrarDto usuarioRegistrar) {
        var usuarioCadastrado = autenticacaoService.cadastrarUsuario(usuarioRegistrar);
        var resposta = RespostaUtils.created(Constants.Success.USUARIO_CADASTRADO_COM_SUCESSO, usuarioCadastrado);
        return ResponseEntity.status(resposta.getStatusCode()).body(resposta);
    }

}
