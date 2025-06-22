package br.com.snapcast.ports.aws;

import br.com.snapcast.configs.aws.CoginitoConfig;
import br.com.snapcast.ports.adapter.cloud.PegarEmail;
import br.com.snapcast.shared.exception.UsuarioNaoCadastro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class PegarEmailCoginitoAWS implements PegarEmail {

    CoginitoConfig config;

    @Override
    public String pegarEmailAtravesUsername(String username) {
        CognitoIdentityProviderClient cognitoClient = config.clienteCogito();

        AdminGetUserRequest request = AdminGetUserRequest.builder()
                .userPoolId(config.getUserPoolId())
                .username(username)
                .build();

        AdminGetUserResponse result = cognitoClient.adminGetUser(request);

        for (AttributeType attr : result.userAttributes()) {
            if ("email".equals(attr.name())) {
                return attr.value();
            }
        }

        throw new UsuarioNaoCadastro();
    }

}
