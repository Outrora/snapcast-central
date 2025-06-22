package br.com.snapcast.configs.aws;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Getter
@ApplicationScoped
public class CoginitoConfig {

    @ConfigProperty(name = "aws.id", defaultValue = "SEU_CLIENT_ID")
    private String clientId;

    @ConfigProperty(name = "aws.regiao", defaultValue = "us-east-1")
    private String userPoolRegion;

    @ConfigProperty(name = "aws.userPool", defaultValue = "us-east-1_uNkmruzvP")
    private String userPoolId;

    public CognitoIdentityProviderClient clienteCogito() {
        return CognitoIdentityProviderClient.builder()
                .region(Region.of(userPoolRegion))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
    }

}
