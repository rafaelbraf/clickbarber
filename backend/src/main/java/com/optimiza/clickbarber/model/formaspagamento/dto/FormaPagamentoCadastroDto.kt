package com.optimiza.clickbarber.model.formaspagamento.dto

import java.util.UUID

data class FormaPagamentoCadastroDto(
    val tipo: String,
    val ativo: Boolean,
    val idExternoBarbearia: UUID
)
