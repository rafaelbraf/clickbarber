package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.agendamento.Agendamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import java.util.*

@Repository
interface AgendamentoRepository : JpaRepository<Agendamento, Long> {
    fun findByIdExterno(idExterno: UUID): Optional<Agendamento>

    @Query("""
        SELECT a FROM Agendamento a
        JOIN FETCH a.barbearia b
        JOIN FETCH a.servicos
        JOIN FETCH a.barbeiros
        JOIN FETCH a.cliente
        LEFT JOIN FETCH b.usuario u
        WHERE b.idExterno = :idExternoBarbearia    
    """)
    fun findByIdExternoBarbearia(@Param("idExternoBarbearia") idExternoBarbearia: UUID): List<Agendamento>

    fun findByDataHoraAndBarbeariaId(dataHora: ZonedDateTime?, barbeariaId: Int?): List<Agendamento>

}