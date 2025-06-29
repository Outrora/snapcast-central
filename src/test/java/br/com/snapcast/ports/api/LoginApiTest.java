package br.com.snapcast.ports.api;

import br.com.snapcast.domain.user_cases.LoginUserCase;
import br.com.snapcast.ports.rest.api.login.LoginApi;
import br.com.snapcast.ports.rest.request.CriarUsuarioRequest;
import br.com.snapcast.ports.rest.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class LoginApiTest {

    @Mock
    LoginUserCase userCase;

    LoginApi api;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        api = new LoginApi(userCase);
    }

    @Test
    void seLoginConSucesso() {
        var token = "fdghdfjkhgjkshgjkvhdfgjkhsjkd";
        var request = new LoginRequest("ghjkdfh@gjkldfj.com", "123456");
        when(userCase.authenticate(request.email(), request.senha()))
                .thenReturn(token);

        var response = api.login(request);

        assertThat(response).isNotEmpty().isEqualTo(token);


    }

    @Test
    void seCriarUsuarioConSucesso() {
        var request = new CriarUsuarioRequest("dfgdjh@ghjkdfh.com", "123456");
        doNothing().when(userCase).criarUsuario(any(), any());
        var response = api.criarUsuario(request);
        assertThat(response.getStatus()).isEqualTo(202);
    }

}
