package com.optimiza.clickbarber.model.horariofuncionamento

import com.fasterxml.jackson.annotation.JsonBackReference
import com.optimiza.clickbarber.model.barbearia.Barbearia
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalTime

@Table(name = "horario_funcionamento")
@Entity
class HorarioFuncionamento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @ManyToOne
    @JoinColumn(name = "barbearia_id")
    @JsonBackReference
    val barbearia: Barbearia,

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    val diaDaSemana: DiaDaSemanaEnum,

    val horaAbertura: LocalTime,
    val horaFechamento: LocalTime
) {
    constructor() : this(null, Barbearia(), DiaDaSemanaEnum.SEGUNDA, LocalTime.now(), LocalTime.now())
}