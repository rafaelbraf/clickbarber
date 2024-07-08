package com.optimiza.clickbarber.model;

import com.optimiza.clickbarber.model.autenticacao.RespostaAutenticacao;
import org.springframework.http.HttpStatus;

public class RespostaUtils {

    private RespostaUtils() {}

    public static <T> Resposta<T> criarResposta(int statusCode, boolean success, String message, T result) {
        return Resposta.<T>builder()
                .statusCode(statusCode)
                .success(success)
                .message(message)
                .result(result)
                .build();
    }

    public static <T> RespostaAutenticacao<T> criarResposta(int statusCode, boolean success, String message, T result, String accessToken) {
        return RespostaAutenticacao.<T>builder()
                .statusCode(statusCode)
                .message(message)
                .success(success)
                .result(result)
                .accessToken(accessToken)
                .build();
    }

    public static <T> Resposta<T> ok(String message, T result) {
        return criarResposta(HttpStatus.OK.value(), true, message, result);
    }

    public static <T> Resposta<T> created(String message, T result) {
        return criarResposta(HttpStatus.CREATED.value(), true, message, result);
    }

    public static <T> Resposta<T> noContent() {
        return criarResposta(HttpStatus.NO_CONTENT.value(), true, null, null);
    }

    public static <T> Resposta<T> error(String message, T result) {
        return criarResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, message, result);
    }

    public static <T> Resposta<T> conflict(String message, T result) {
        return criarResposta(HttpStatus.CONFLICT.value(), false, message, result);
    }

    public static <T> Resposta<T> notFound(String message, T result) {
        return criarResposta(HttpStatus.NOT_FOUND.value(), false, message, result);
    }

    public static <T> RespostaAutenticacao<T> authorized(String message, T result, String accessToken) {
        return criarResposta(HttpStatus.OK.value(), true, message, result, accessToken);
    }

    public static <T> RespostaAutenticacao<T> unauthorized(String message, T result) {
        return criarResposta(HttpStatus.UNAUTHORIZED.value(), false, message, result, null);
    }

}
