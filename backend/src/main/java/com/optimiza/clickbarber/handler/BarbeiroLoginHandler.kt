package com.optimiza.clickbarber.handler

import com.optimiza.clickbarber.config.JwtUtil
import com.optimiza.clickbarber.model.Role
import com.optimiza.clickbarber.model.autenticacao.RespostaLogin
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto
import com.optimiza.clickbarber.model.usuario.Usuario
import com.optimiza.clickbarber.service.BarbeiroService

class BarbeiroLoginHandler(
    private val barbeiroService: BarbeiroService,
    private val jwtUtil: JwtUtil
) : LoginHandler {

    override fun handle(loginRequestDto: LoginRequestDto, usuario: Usuario): RespostaLogin? {
        return if (usuario.role == Role.BARBEIRO) {
            val barbeiro = barbeiroService.buscarPorUsuarioId(usuario.id)

            RespostaLogin.authorized(barbeiro, jwtUtil.gerarToken(usuario.email))
        } else {
            null
        }
    }

}