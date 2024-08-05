package com.optimiza.clickbarber.model.horariofuncionamento

import java.time.LocalTime

data class HorarioFuncionamentoDto(
    val diaDaSemana: DiaDaSemanaEnum,
    val horaAbertura: LocalTime,
    val horaFechamento: LocalTime
)
