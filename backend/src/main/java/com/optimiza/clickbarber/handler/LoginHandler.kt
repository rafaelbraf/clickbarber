package com.optimiza.clickbarber.handler

import com.optimiza.clickbarber.model.autenticacao.RespostaLogin
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto
import com.optimiza.clickbarber.model.usuario.Usuario

fun interface LoginHandler {
    fun handle(loginRequestDto: LoginRequestDto, usuario: Usuario): RespostaLogin?
}