package br.com.snapcast.shared.provider;

import static br.com.snapcast.shared.provider.RespostaErro.criarRespostaErro;

import br.com.snapcast.shared.exception.base.BaseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.java.Log;
import software.amazon.awssdk.services.cognitoidentityprovider.model.NotAuthorizedException;

@Provider
@Log
public class ErroProvider implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof BaseException ex) {
            return criarRespostaErro(ex);
        }

        if (exception instanceof NotAuthorizedException ex) {
            return criarRespostaErro(ex, Response.Status.FORBIDDEN, "Senha ou email incorreto");
        }

        if (exception instanceof IllegalArgumentException ex) {
            return criarRespostaErro(ex, Response.Status.BAD_REQUEST, "Argumento Incorreto");
        }

        return criarRespostaErro(
                exception,
                Response.Status.INTERNAL_SERVER_ERROR);
    }

}
