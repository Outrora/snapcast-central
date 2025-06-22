package br.com.snapcast.ports.s3;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

import br.com.snapcast.configs.aws.S3Config;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class GeradorDeLink {

    S3Config s3;

    public URL criarLinkTemporario(String key) {
        try (S3Presigner presigner = s3.geraPresignRequest()) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(s3.getBucket())
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = s3.geraObjectPresignRequest(getObjectRequest);

            URL url = presigner.presignGetObject(presignRequest).url();

            System.out.println("URL temporária: " + url);
            return url;
        }
    }
}
