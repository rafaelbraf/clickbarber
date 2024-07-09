package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.servico.Servico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ServicoRepository : JpaRepository<Servico?, Long?> {

    @Query("SELECT s FROM Servico s INNER JOIN Barbearia b ON b.id = s.barbearia.id WHERE b.idExterno = :idExternoBarbearia")
    fun findByIdExternoBarbearia(@Param("idExternoBarbearia") idExternoBarbearia: UUID?): List<Servico?>?

}