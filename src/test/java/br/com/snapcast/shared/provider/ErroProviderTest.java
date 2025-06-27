package br.com.snapcast.shared.provider;

import br.com.snapcast.shared.exception.UsuarioNaoCadastro;
import jakarta.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.cognitoidentityprovider.model.NotAuthorizedException;

import static org.assertj.core.api.Assertions.assertThat;

class ErroProviderTest {

    ErroProvider provider;

    @BeforeEach
    void setup() {
        provider = new ErroProvider();
    }

    @Test
    void TestarSeAoUsuarioNaoCadastro() {
        var exception = new UsuarioNaoCadastro();

        var resposta = provider.toResponse(exception);

        assertThat(resposta.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        assertThat(resposta.getEntity()).isInstanceOf(RespostaErro.class);
    }

    @Test
    void TestarSeAoReceberOutroErroQualquer() {
        var exception = new Exception();

        var resposta = provider.toResponse(exception);

        assertThat(resposta.getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertThat(resposta.getEntity()).isInstanceOf(RespostaErro.class);
    }

    @Test
    void TestarSeAoReceberNotAuthorizedException() {
        NotAuthorizedException exception = NotAuthorizedException.builder().build();

        var resposta = provider.toResponse(exception);

        assertThat(resposta.getStatus()).isEqualTo(Status.FORBIDDEN.getStatusCode());
        assertThat(resposta.getEntity()).isInstanceOf(RespostaErro.class);
    }

    @Test
    void TestarSeAoReceberIllegalArgumentException() {
        var exception = new IllegalArgumentException();

        var resposta = provider.toResponse(exception);

        assertThat(resposta.getStatus()).isEqualTo(Status.BAD_REQUEST.getStatusCode());
        assertThat(resposta.getEntity()).isInstanceOf(RespostaErro.class);
    }


}
