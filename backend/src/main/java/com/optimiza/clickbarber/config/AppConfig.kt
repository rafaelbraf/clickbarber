package com.optimiza.clickbarber.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.optimiza.clickbarber.handler.BarbeariaLoginHandler
import com.optimiza.clickbarber.handler.BarbeiroLoginHandler
import com.optimiza.clickbarber.handler.ClienteLoginHandler
import com.optimiza.clickbarber.handler.LoginHandlerChain
import com.optimiza.clickbarber.service.AutenticacaoService
import com.optimiza.clickbarber.service.BarbeariaService
import com.optimiza.clickbarber.service.BarbeiroService
import com.optimiza.clickbarber.service.ClienteService
import com.optimiza.clickbarber.service.UsuarioService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AppConfig {

    @Bean
    open fun loginHandlerChain(
        clienteService: ClienteService,
        barbeiroService: BarbeiroService,
        barbeariaService: BarbeariaService,
        jwtUtil: JwtUtil
    ): LoginHandlerChain {
        val handlers = listOf(
            ClienteLoginHandler(clienteService = clienteService, jwtUtil = jwtUtil),
            BarbeariaLoginHandler(barbeariaService = barbeariaService, jwtUtil = jwtUtil),
            BarbeiroLoginHandler(barbeiroService = barbeiroService, jwtUtil = jwtUtil)
        )

        return LoginHandlerChain(handlers)
    }

    @Bean
    open fun loginService(
        usuarioService: UsuarioService,
        loginHandlerChain: LoginHandlerChain,
        barbeariaService: BarbeariaService,
        jwtUtil: JwtUtil,
        objectMapper: ObjectMapper,
        clienteService: ClienteService,
        barbeiroService: BarbeiroService
    ): AutenticacaoService {
        return AutenticacaoService(
            barbeariaService,
            jwtUtil,
            usuarioService,
            objectMapper,
            clienteService,
            barbeiroService,
            loginHandlerChain
        )
    }

}