package com.optimiza.clickbarber.utils

object Constants {
    const val BEARER_PREFIX: String = "Bearer "
    const val AUTHORIZATION_NAME: String = "Authorization"
    const val OPTIONS_METHOD: String = "OPTIONS"

    object Success {
        const val LOGIN_REALIZADO_COM_SUCESSO: String = "Login realizado com sucesso!"
        const val BARBEARIAS_ENCONTRADAS_PELO_NOME: String = "Barbearias encontradas com nome"
        const val BARBEARIAS_ENCONTRADA_PELO_ID_EXTERNO: String = "Barbearias encontradas com idExterno"
        const val BARBEARIA_ATUALIZADA_SUCESSO: String = "Barbearia atualizada com sucesso!"
        const val BARBEARIAS_ENCONTRADAS: String = "Barbearia(s) encontrada(s)"
        const val SERVICOS_ENCONTRADOS: String = "Serviços encontrados"
        const val SERVICO_ENCONTRADO_COM_SUCESSO: String = "Serviço encontrado com sucesso!"
        const val SERVICOS_ENCONTRADOS_DA_BARBEARIA: String = "Serviços encontrados para Barbearia com id "
        const val SERVICO_CADASTRADO_COM_SUCESSO: String = "Serviço cadastrado com sucesso!"
        const val SERVICO_ATUALIZADO_COM_SUCESSO: String = "Serviço atualizado com sucesso!"
        const val BARBEIROS_ENCONTRADOS_DA_BARBEARIA: String = "Barbeiros encontrados para Barbearia com id "
        const val BARBEIRO_ENCONTRADO_COM_SUCESSO: String = "Barbeiro encontrado com sucesso!"
        const val BARBEIRO_ATUALIZADO_COM_SUCESSO: String = "Barbeiro atualizado com sucesso!"
        const val BARBEIRO_CADASTRADO_COM_SUCESSO: String = "Barbeiro cadastrado com sucesso!"
        const val USUARIO_CADASTRADO_COM_SUCESSO: String = "Usuário cadastrado com sucesso!"
        const val AGENDAMENTO_ENCONTRADO_COM_SUCESSO: String = "Agendamento encontrado com sucesso!"
        const val AGENDAMENTOS_ENCONTRADOS: String = "Agendamentos encontrados para Barbearia com id "
        const val AGENDAMENTO_CADASTRADO_COM_SUCESSO: String = "Agendamento cadastrado com sucesso!"
        const val AGENDAMENTO_ATUALIZADO_COM_SUCESSO: String = "Agendamento atualizado com sucesso!"
        const val CLIENTES_ENCONTRADOS_DA_BARBEARIA: String = "Clientes encontrados para Barbearia com id "
    }

    object Error {
        const val EMAIL_OU_SENHA_INCORRETA: String = "Email ou senha incorreta!"
        const val RESOURCE_NOT_FOUND_EXCEPTION: String = "Não foi encontrado(a) %s com %s = %s."
        const val ERRO_AO_CADASTRAR_OBJETO: String = "Erro ao cadastrar objeto."
        const val EXISTE_BARBEARIA_COM_ESSE_CNPJ: String = "Já existe barbearia cadastrada com esse CNPJ."
        const val EXISTE_BARBEARIA_COM_ESSE_EMAIL: String = "Já existe barbearia cadastrada com esse Email."
        const val ERRO_INTERNO_SERVIDOR: String = "Erro interno do servidor."
        const val BARBEARIA_NAO_ENCONTRADA_PRO_SERVICO: String = "Barbearia não encontrada pro serviço."
        const val RESOURCE_NOT_FOUND: String = "%s não encontrado(a)."
        const val TOKEN_INVALIDO: String = "Token inválido!"
        const val TOKEN_EXPIRADO: String = "Token expirado!"
        const val EXISTE_BARBEIRO_COM_ESSE_CPF: String = "Já existe Barbeiro cadastrado com esse CPF."
        const val EXISTE_BARBEIRO_COM_ESSE_EMAIL: String = "Já existe Barbeiro cadastrado com esse email."
        const val BARBEARIA_NAO_ENCONTRADA_PRO_BARBEIRO: String = "Barbearia não encontrada pro Barbeiro."
        const val EXISTE_USUARIO_COM_ESSE_CPF: String = "Já existe Usuário cadastrado com esse CPF."
        const val EXISTE_USUARIO_COM_ESSE_EMAIL: String = "Já existe Usuário cadastrado com esse email."
        const val BARBEARIA_NAO_ENCONTRADA_PRO_AGENDAMENTO: String = "Barbearia não encontrada pro Agendamento."
        const val USUARIO_NAO_ENCONTRADO_PRO_AGENDAMENTO: String = "Usuário não encontrado pro Agendamento."
        const val EMAIL_NAO_PODE_SER_NULO: String = "Email não pode ser nulo."
        const val SENHA_NAO_PODE_SER_NULA: String = "Senha não pode ser nula."
        const val CLIENTE_NAO_ENCONTRADO_PRO_AGENDAMENTO: String = "Cliente não encontrado pro Agendamento."
        const val SERVICO_NAO_ENCONTRADO_PRO_AGENDAMENTO: String = "Serviço não encontrado pro Agendamento."
        const val ENTIDADE_NAO_ENCONTRADA_PARA_BARBEARIA: String = "%s com ID %s não encontrado para Barbearia com ID Externo %s"
        const val BARBEIRO_POSSUI_RELACAO_COM_AGENDAMENTOS: String = "Barbeiro não pode ser excluído pois possui relação com agendamentos."
    }

    object UrlPattern {
        const val BARBEARIAS_URL_PATTERN: String = "/barbearias/*"
        const val SERVICOS_URL_PATTERN: String = "/servicos/*"
        const val BARBEIROS_URL_PATTERN: String = "/barbeiros/*"
        const val AGENDAMENTOS_URL_PATTERN: String = "/agendamentos/*"
    }

    object Entity {
        const val BARBEARIA: String = "Barbearia"
        const val SERVICO: String = "Servico"
        const val BARBEIRO: String = "Barbeiro"
        const val USUARIO: String = "Usuario"
        const val AGENDAMENTO: String = "Agendamento"
        const val CLIENTE: String = "Cliente"
    }

    object Attribute {
        const val ID: String = "id"
        const val EMAIL: String = "email"
        const val USUARIO_ID: String = "usuario_id"
        const val ID_EXTERNO: String = "id_externo"
    }

}