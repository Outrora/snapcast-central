package br.com.snapcast.domain.user_cases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.snapcast.ports.adapter.cloud.Login;
import br.com.snapcast.shared.exception.CamposInvalidos;

class LoginUserCaseTest {

    @Mock
    Login login;

    LoginUserCase userCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userCase = new LoginUserCase(login);
    }

    @Test
    void verificarAtenticaoEstarFuncionadoCorretamente() {

        var email = "test@gmail.com";
        var senha = "teste1234567";

        when(login.authenticate(anyString(), anyString())).thenReturn("token");

        userCase.authenticate(email, senha);

        verify(login, times(1)).authenticate(email, senha);
    }

    @Test
    void verificarAtenticaoSenhaInvalida() {

        var email = "test@gmail.com";
        var senha = "";

        when(login.authenticate(anyString(), anyString())).thenReturn("token");

        assertThatThrownBy(() -> {
            userCase.authenticate(email, senha);
        })
                .isInstanceOf(CamposInvalidos.class)
                .hasMessageContaining("Email e/ou senha em branco");

        verify(login, times(0)).authenticate(email, senha);
    }

    @Test
    void verificarAtenticaoSenhaInvalida2() {

        var email = "test@gmail.com";
        var senha = "sadd";

        when(login.authenticate(anyString(), anyString())).thenReturn("token");

        assertThatThrownBy(() -> {
            userCase.authenticate(email, senha);
        })
                .isInstanceOf(CamposInvalidos.class)
                .hasMessageContaining("Senha invalida");

        verify(login, times(0)).authenticate(email, senha);
    }

    @Test
    void verificarAtenticaoEmailInvalida() {

        var email = "test@";
        var senha = "teste1234567";

        when(login.authenticate(anyString(), anyString())).thenReturn("token");

        assertThatThrownBy(() -> {
            userCase.authenticate(email, senha);
        })
                .isInstanceOf(CamposInvalidos.class)
                .hasMessageContaining("Email inválido");

        verify(login, times(0)).authenticate(email, senha);
    }

    @Test
    void verificarAtenticaoEmailInvalida2() {

        var email = "";
        var senha = "teste1234567";

        when(login.authenticate(anyString(), anyString())).thenReturn("token");

        assertThatThrownBy(() -> {
            userCase.authenticate(email, senha);
        })
                .isInstanceOf(CamposInvalidos.class)
                .hasMessageContaining("Email e/ou senha em branco");

        verify(login, times(0)).authenticate(email, senha);
    }

    @Test
    void verificarCriarUsuarioCorretamente() {
        var email = "test@gmail.com";
        var senha = "teste1234567";

        doNothing().when(login).criarUsuario(anyString(), anyString());

        userCase.criarUsuario(email, senha);

        verify(login, times(1)).criarUsuario(email, senha);

    }

}
