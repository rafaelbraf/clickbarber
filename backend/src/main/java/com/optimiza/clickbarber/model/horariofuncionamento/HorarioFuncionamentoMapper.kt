package com.optimiza.clickbarber.model.horariofuncionamento

import org.springframework.stereotype.Component

@Component
class HorarioFuncionamentoMapper {

    fun toDto(horarioFuncionamento: HorarioFuncionamento): HorarioFuncionamentoDto {
        return HorarioFuncionamentoDto(
            diaDaSemana = horarioFuncionamento.diaDaSemana,
            horaAbertura = horarioFuncionamento.horaAbertura,
            horaFechamento = horarioFuncionamento.horaFechamento
        )
    }

}