package com.optimiza.clickbarber.exception

class CadastroAgendamentoException(
    message: String
) : RuntimeException("Erro ao cadastrar agendamento: $message")