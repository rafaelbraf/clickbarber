package com.optimiza.clickbarber.repository

import com.optimiza.clickbarber.model.horariofuncionamento.HorarioFuncionamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HorarioFuncionamentoRepository : JpaRepository<HorarioFuncionamento, Long> {

    fun findByBarbeariaId(barbeariaId: Long): List<HorarioFuncionamento>

}