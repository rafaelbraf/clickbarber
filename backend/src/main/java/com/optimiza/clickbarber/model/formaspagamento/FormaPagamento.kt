package com.optimiza.clickbarber.model.formaspagamento

import com.fasterxml.jackson.annotation.JsonBackReference
import com.optimiza.clickbarber.model.barbearia.Barbearia
import com.optimiza.clickbarber.model.formaspagamento.dto.FormaPagamentoRespostaDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import java.util.UUID

@Table(name = "formas_pagamento")
@Entity
class FormaPagamento(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    var idExterno: UUID?,

    val tipo: String,

    val ativo: Boolean,

    @ManyToOne
    @JoinColumn(name = "barbearia_id")
    @JsonBackReference
    val barbearia: Barbearia

) {

    constructor() : this(
        id = null,
        idExterno = null,
        tipo = "",
        ativo = true,
        barbearia = Barbearia()
    )

    constructor(tipo: String, barbearia: Barbearia, ativo: Boolean): this(
        id = null,
        idExterno = null,
        tipo = tipo,
        ativo = ativo,
        barbearia = barbearia
    )

    @PrePersist
    fun gerarIdExterno() {
        if (idExterno == null) {
            idExterno = UUID.randomUUID()
        }
    }

}

fun FormaPagamento.toRespostaDto(): FormaPagamentoRespostaDto {
    return FormaPagamentoRespostaDto(
        idExterno = this.idExterno!!,
        tipo = this.tipo,
        ativo = this.ativo
    )
}