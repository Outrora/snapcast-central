package br.com.snapcast.domain.user_cases;

import java.util.HashMap;
import java.util.Map;

import br.com.snapcast.configs.aws.CoginitoConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ChallengeNameType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.RespondToAuthChallengeRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.RespondToAuthChallengeResponse;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class LoginUserCase {

    private CoginitoConfig config;

    public String authenticate(String email, String senha) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", email);
        authParams.put("PASSWORD", senha);

        InitiateAuthRequest authRequest = InitiateAuthRequest.builder()
                .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                .clientId(config.getClientId())
                .authParameters(authParams)
                .build();

        log.info("👶 Usuário Realizando login");
        InitiateAuthResponse response = config.clienteCogito().initiateAuth(authRequest);

        // Verifica se há challenge pendente
        var challenge = response.challengeName();
        if (challenge == ChallengeNameType.NEW_PASSWORD_REQUIRED) {
            log.warning("👶 Usuário precisa alterar a senha: " + email);

            Map<String, String> challengeResponses = new HashMap<>();
            challengeResponses.put("USERNAME", email);
            challengeResponses.put("NEW_PASSWORD", senha);

            RespondToAuthChallengeRequest challengeRequest = RespondToAuthChallengeRequest.builder()
                    .clientId(config.getClientId())
                    .challengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                    .session(response.session())
                    .challengeResponses(challengeResponses)
                    .build();

            RespondToAuthChallengeResponse finalResponse = config.clienteCogito()
                    .respondToAuthChallenge(challengeRequest);

            return finalResponse.authenticationResult().accessToken();
        }

        if (response.authenticationResult() == null) {
            log.warning("🚫 Falha na autenticação do usuário: " + email);
            throw new RuntimeException("🚫 Usuário ou senha inválidos ou autenticação não concluída.");
        }

        return response.authenticationResult().accessToken();

    }

    public void criarUsuario(String email, String senha) {
        log.info("👶 Criando Usuário");
        AdminCreateUserRequest request = AdminCreateUserRequest.builder()
                .userPoolId(config.getUserPoolId())
                .username(email)
                .userAttributes(
                        a -> a.name("email").value(email),
                        a -> a.name("email_verified").value("true"))
                .temporaryPassword(senha)
                .messageAction("SUPPRESS")
                .build();

        config.clienteCogito().adminCreateUser(request);
        log.info("👶 Usuário criado no Cognito: " + email);
    }

}
