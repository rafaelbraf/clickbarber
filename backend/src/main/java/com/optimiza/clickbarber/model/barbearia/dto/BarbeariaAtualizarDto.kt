package com.optimiza.clickbarber.model.barbearia.dto

import java.util.UUID

data class BarbeariaAtualizarDto(
    val idExterno: UUID,
    val nome: String,
    val cnpj: String,
    val endereco: String,
    val telefone: String,
    val email: String
)
