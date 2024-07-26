package com.optimiza.clickbarber.exception

class AtualizacaoEntidadeException(
    entidadeNome: String,
    identificadorEntidade: String,
    mensagem: String,
    causa: Throwable?
) : RuntimeException(
    "Erro ao atualizar $entidadeNome com identificador $identificadorEntidade: $mensagem",
    causa
)