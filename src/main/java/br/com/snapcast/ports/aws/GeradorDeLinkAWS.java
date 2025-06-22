package br.com.snapcast.ports.aws;

import java.net.URL;

import br.com.snapcast.configs.aws.S3Config;
import br.com.snapcast.ports.adapter.cloud.GeradorDeLinks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class GeradorDeLinkAWS implements GeradorDeLinks {

    S3Config s3;

    @Override
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
