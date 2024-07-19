package com.optimiza.clickbarber.handler

import com.optimiza.clickbarber.config.JwtUtil
import com.optimiza.clickbarber.model.Role
import com.optimiza.clickbarber.model.autenticacao.RespostaLogin
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto
import com.optimiza.clickbarber.model.usuario.Usuario
import com.optimiza.clickbarber.service.ClienteService

class ClienteLoginHandler(
    private val clienteService: ClienteService,
    private val jwtUtil: JwtUtil
) : LoginHandler {
    override fun handle(loginRequestDto: LoginRequestDto, usuario: Usuario): RespostaLogin? {
        return if (usuario.role == Role.CLIENTE) {
            val cliente = clienteService.buscarPorUsuarioId(usuarioId = usuario.id!!)

            RespostaLogin.authorized(cliente, jwtUtil.gerarToken(usuario.email))
        } else {
            null
        }
    }
}