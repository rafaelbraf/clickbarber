package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.formaspagamento.FormaPagamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface FormaPagamentoRepository : JpaRepository<FormaPagamento, Long> {

    fun findByIdExterno(idExterno: UUID): Optional<FormaPagamento>
    fun findByBarbeariaId(barbeariaId: Long): List<FormaPagamento>

}