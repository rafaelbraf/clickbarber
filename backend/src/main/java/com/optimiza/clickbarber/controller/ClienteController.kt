package com.optimiza.clickbarber.controller

import com.optimiza.clickbarber.model.Resposta
import com.optimiza.clickbarber.model.RespostaUtils
import com.optimiza.clickbarber.model.cliente.dto.ClienteDto
import com.optimiza.clickbarber.service.ClienteService
import com.optimiza.clickbarber.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/clientes")
class ClienteController @Autowired constructor(
    private val clienteService: ClienteService
) {

    @GetMapping("/barbearia/{idExternoBarbearia}")
    fun buscarPorIdExternoBarbearia(@PathVariable idExternoBarbearia: UUID): Resposta<List<ClienteDto>> {
        val clientes = clienteService.buscarPorIdExternoBarbearia(idExternoBarbearia)
        val mensagem = "${Constants.Success.CLIENTES_ENCONTRADOS_DA_BARBEARIA}$idExternoBarbearia"
        return RespostaUtils.ok(mensagem, clientes)
    }

}