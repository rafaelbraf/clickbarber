package com.optimiza.clickbarber.controller

import com.optimiza.clickbarber.service.ClienteService
import com.optimiza.clickbarber.utils.Constants
import com.optimiza.clickbarber.utils.TestDataFactory.montarClienteDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(value = [ClienteController::class], useDefaultFilters = false)
internal class ClienteControllerTest {

    @Autowired
    private var mockMvc: MockMvc? = null

    @Mock
    private val clienteService: ClienteService? = null

    private var idExternoBarbearia: UUID? = null
    private var idExternoCliente1: UUID? = null
    private var idExternoCliente2: UUID? = null

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ClienteController(clienteService!!)).build()
        idExternoBarbearia = UUID.randomUUID()
        idExternoCliente1 = UUID.randomUUID()
        idExternoCliente2 = UUID.randomUUID()
    }

    @Test
    fun testBuscarClientesPeloIdExternoDaBarbearia() {
        val cliente1 = montarClienteDto(idExternoCliente1)
        val cliente2 = montarClienteDto(idExternoCliente2)
        val listaClientes = listOf(cliente1, cliente2)
        `when`(clienteService!!.buscarPorIdExternoBarbearia(idExternoBarbearia!!)).thenReturn(listaClientes)

        mockMvc!!.perform(get("/clientes/barbearia/{idExternoBarbearia}", idExternoBarbearia))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("${Constants.Success.CLIENTES_ENCONTRADOS_DA_BARBEARIA}$idExternoBarbearia"))
            .andExpect(jsonPath("$.result.[0].idExterno").value(idExternoCliente1.toString()))
            .andExpect(jsonPath("$.result.[1].idExterno").value(idExternoCliente2.toString()))
    }

}