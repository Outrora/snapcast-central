package br.com.snapcast.configs.aws;

import java.time.Duration;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Getter
@NoArgsConstructor
@ApplicationScoped
public class S3Config {

    @ConfigProperty(name = "aws.bucket.name", defaultValue = "snapcastvideos")
    private String bucket;

    @ConfigProperty(name = "aws.regiao", defaultValue = "us-east-1")
    private String userPoolRegion;

    public GetObjectPresignRequest geraObjectPresignRequest(GetObjectRequest getObjectRequest) {
        return GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(15))
                .getObjectRequest(getObjectRequest)
                .build();
    }

    public S3Presigner geraPresignRequest() {
        return S3Presigner.builder()
                .region(Region.of(userPoolRegion))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())
                .build();
    }
}
