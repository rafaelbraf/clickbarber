package com.optimiza.clickbarber.exception;

import com.optimiza.clickbarber.model.Resposta;
import com.optimiza.clickbarber.model.RespostaUtils;
import com.optimiza.clickbarber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final Map<String, String> constraintsErrorMessages = new HashMap<>();

    static {
        constraintsErrorMessages.put("barbearias_cnpj_key", Constants.Error.EXISTE_BARBEARIA_COM_ESSE_CNPJ);
        constraintsErrorMessages.put("barbearias_email_key", Constants.Error.EXISTE_BARBEARIA_COM_ESSE_EMAIL);
        constraintsErrorMessages.put("fk_barbearia", Constants.Error.BARBEARIA_NAO_ENCONTRADA_PRO_SERVICO);
        constraintsErrorMessages.put("barbeiros_cpf_key", Constants.Error.EXISTE_BARBEIRO_COM_ESSE_CPF);
        constraintsErrorMessages.put("barbeiros_email_key", Constants.Error.EXISTE_BARBEIRO_COM_ESSE_EMAIL);
        constraintsErrorMessages.put("barbeiros_barbearia_id_fkey", Constants.Error.BARBEARIA_NAO_ENCONTRADA_PRO_BARBEIRO);
        constraintsErrorMessages.put("usuarios_cpf_key", Constants.Error.EXISTE_USUARIO_COM_ESSE_CPF);
        constraintsErrorMessages.put("usuarios_email_key", Constants.Error.EXISTE_USUARIO_COM_ESSE_EMAIL);
        constraintsErrorMessages.put("agendamentos_barbearia_id_fkey", Constants.Error.BARBEARIA_NAO_ENCONTRADA_PRO_AGENDAMENTO);
        constraintsErrorMessages.put("agendamentos_usuario_id_fkey", Constants.Error.USUARIO_NAO_ENCONTRADO_PRO_AGENDAMENTO);
        constraintsErrorMessages.put("agendamentos_cliente_id_fkey", Constants.Error.CLIENTE_NAO_ENCONTRADO_PRO_AGENDAMENTO);
        constraintsErrorMessages.put("agendamentos_servicos_servico_id_fkey", Constants.Error.SERVICO_NAO_ENCONTRADO_PRO_AGENDAMENTO);
        constraintsErrorMessages.put("agendamentos_barbeiros_barbeiro_id_fkey", Constants.Error.BARBEIRO_POSSUI_RELACAO_COM_AGENDAMENTOS);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Resposta<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String mensagem;

        if (e.getRootCause() instanceof SQLException sqlException) {
            logger.error("DataIntegrityViolationException: {}", e.getMessage());
            var sqlMensagemErro = sqlException.getMessage();
            mensagem = defineMensagemDeErroSQL(sqlMensagemErro);
            var resposta = RespostaUtils.conflict(mensagem, e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
        }

        logger.error("Internal Server Error: {}", e.getMessage());
        mensagem = Constants.Error.ERRO_INTERNO_SERVIDOR;
        var resposta = RespostaUtils.error(mensagem, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Resposta<String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        logger.error("ResourceNotFoundException: {}", e.getMessage());
        var mensagem = String.format(Constants.Error.RESOURCE_NOT_FOUND, e.getResourceName());
        var resposta = RespostaUtils.notFound(mensagem, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(CadastroAgendamentoException.class)
    public ResponseEntity<Resposta<String>> handleCadastroAgendamentoException(CadastroAgendamentoException e) {
        logger.error("CadastroAgendamentoException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespostaUtils.error("Erro ao cadastrar agendamento", e.getMessage()));
    }

    private String defineMensagemDeErroSQL(String mensagemErroSQL) {
        return constraintsErrorMessages.entrySet().stream()
                .filter(entry -> mensagemErroSQL.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(Constants.Error.ERRO_AO_CADASTRAR_OBJETO);
    }

}
