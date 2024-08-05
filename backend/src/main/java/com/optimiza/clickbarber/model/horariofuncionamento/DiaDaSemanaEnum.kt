package com.optimiza.clickbarber.model.horariofuncionamento

enum class DiaDaSemanaEnum(
    val codigo: Int,
    val nome: String
) {

    DOMINGO(1, "Domingo"),
    SEGUNDA(2, "Segunda"),
    TERCA(3, "Terça"),
    QUARTA(4, "Quarta"),
    QUINTA(5, "Quinta"),
    SEXTA(6, "Sexta"),
    SABADO(7, "Sábado");

}