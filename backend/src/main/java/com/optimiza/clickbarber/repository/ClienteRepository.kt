package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.cliente.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun findByIdExterno(idExterno: UUID): Optional<Cliente>

    fun findByUsuarioId(usuarioId: Long): Optional<Cliente>

    @Query("""
        SELECT c FROM Cliente c
        INNER JOIN Agendamento a ON a.cliente = c
        INNER JOIN Barbearia b ON a.barbearia = b
        WHERE b.idExterno = :idExternoBarbearia
    """)
    fun findByIdExternoBarbearia(idExternoBarbearia: UUID): List<Cliente>
}