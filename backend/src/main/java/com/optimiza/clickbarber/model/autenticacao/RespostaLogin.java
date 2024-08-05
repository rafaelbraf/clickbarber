package com.optimiza.clickbarber.model.autenticacao;

import com.optimiza.clickbarber.utils.Constants;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespostaLogin {

    private boolean success;
    private String message;
    private Object result;
    private String accessToken;

    private static RespostaLogin criarRespostaLogin(boolean success, String message, Object result, String accessToken) {
        return RespostaLogin.builder()
                .success(success)
                .message(message)
                .result(result)
                .accessToken(accessToken)
                .build();
    }

    public static RespostaLogin authorized(Object result, String accessToken) {
        return criarRespostaLogin(true, Constants.Success.LOGIN_REALIZADO_COM_SUCESSO, result, accessToken);
    }

    public static RespostaLogin unauthorized(String message) {
        return criarRespostaLogin(false, message, null, null);
    }

}
