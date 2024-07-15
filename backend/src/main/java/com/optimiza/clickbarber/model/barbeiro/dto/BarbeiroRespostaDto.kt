package com.optimiza.clickbarber.model.barbeiro.dto

import java.util.UUID

data class BarbeiroRespostaDto(
    val idExterno: UUID,
    val nome: String,
    val celular: String,
    val ativo: Boolean
)
