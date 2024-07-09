package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.barbeiro.Barbeiro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BarbeiroRepository : JpaRepository<Barbeiro?, Long?> {
    @Query("SELECT b1 FROM Barbeiro b1 INNER JOIN Barbearia b2 ON b1.barbearia.id = b2.id WHERE b2.idExterno = :idExternoBarbearia")
    fun findByIdExternoBarbearia(idExternoBarbearia: UUID?): List<Barbeiro?>?

    fun findByBarbeariaId(id: Int?): List<Barbeiro?>?

    fun findByUsuarioId(usuarioId: Long?): Optional<Barbeiro?>?
}