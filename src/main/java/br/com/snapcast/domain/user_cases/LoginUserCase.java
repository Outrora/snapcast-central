package br.com.snapcast.domain.user_cases;

import br.com.snapcast.ports.adapter.cloud.Login;
import br.com.snapcast.shared.exception.CamposInvalidos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class LoginUserCase {

    Login login;

    public String authenticate(String email, String senha) {

        if (email.isEmpty() || senha.isEmpty()) {
            throw new CamposInvalidos("Email e/ou senha em branco");
        }

        if (senha.length() < 8) {
            throw new CamposInvalidos("Senha invalida");
        }
        log.info("👶 Usuário Realizando login");
        return login.authenticate(email, senha);
    }

    public void criarUsuario(String email, String senha) {
        log.info("👶 Criando Usuário");
        login.criarUsuario(email, senha);
        log.info("👶 Usuário criado no Cognito: " + email);
    }

}
