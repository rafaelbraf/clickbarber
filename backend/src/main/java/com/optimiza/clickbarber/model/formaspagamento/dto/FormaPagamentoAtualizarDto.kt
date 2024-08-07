package com.optimiza.clickbarber.model.formaspagamento.dto

import java.util.UUID

data class FormaPagamentoAtualizarDto(
    val idExterno: UUID,
    val tipo: String,
    val ativo: Boolean
)
