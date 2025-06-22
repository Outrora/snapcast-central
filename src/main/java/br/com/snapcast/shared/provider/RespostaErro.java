package br.com.snapcast.shared.provider;

import java.time.LocalDateTime;

import br.com.snapcast.shared.exception.base.BaseException;
import jakarta.ws.rs.core.Response;

public record RespostaErro(
                String exceptionType,
                int code,
                String error,
                LocalDateTime timestamp) {
        public static Response criarRespostaErro(
                        Exception exception,
                        Response.Status status) {
                var reposta = new RespostaErro(exception.getClass().getName(),
                                status.getStatusCode(),
                                exception.getMessage(),
                                LocalDateTime.now());

                return Response
                                .status(status.getStatusCode())
                                .entity(reposta)
                                .build();
        }

        public static Response criarRespostaErro(
                        BaseException exception) {
                var reposta = new RespostaErro(exception.getClass().getSimpleName(),
                                exception.getStatus().getStatusCode(),
                                exception.getMensagemErro(),
                                LocalDateTime.now());

                return Response
                                .status(exception.getStatus().getStatusCode())
                                .entity(reposta)
                                .build();
        }
}