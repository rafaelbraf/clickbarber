package com.optimiza.clickbarber.handler

import com.optimiza.clickbarber.model.autenticacao.RespostaLogin
import com.optimiza.clickbarber.model.autenticacao.dto.LoginRequestDto
import com.optimiza.clickbarber.model.usuario.Usuario

class LoginHandlerChain(
    private val handlers: List<LoginHandler>
) {

    fun handle(loginRequestDto: LoginRequestDto, usuario: Usuario): RespostaLogin? {
        handlers.forEach { handler ->
            val respostaLogin = handler.handle(loginRequestDto, usuario)
            if (respostaLogin != null) {
                return respostaLogin
            }
        }

        return RespostaLogin.unauthorized()
    }

}