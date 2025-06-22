package br.com.snapcast.shared.exception;

import br.com.snapcast.shared.exception.base.BaseException;
import jakarta.ws.rs.core.Response.Status;

public class CamposInvalidos extends BaseException {

    private final static String MENSAGEM = "❌ A campos invalido: %s";

    public CamposInvalidos(String camposInvalidos) {
        super(Status.BAD_REQUEST, MENSAGEM.formatted(camposInvalidos));

    }

}
