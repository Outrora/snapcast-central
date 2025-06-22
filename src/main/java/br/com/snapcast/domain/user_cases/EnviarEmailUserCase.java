package br.com.snapcast.domain.user_cases;

import java.util.concurrent.CompletableFuture;

import br.com.snapcast.domain.entity.StatusVideoId;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class EnviarEmailUserCase {

    Mailer mailer;

    public void enviarEmailErro(StatusVideoId status, String email) {

        var corpo = """
                ❌ Ao processar o video com id: %s.
                Ocorreu um falha.
                Envie o Arquivo novamente
                """.formatted(status.id());

        CompletableFuture.runAsync(() -> {
            mailer.send(
                    Mail.withText(email,
                            "Falha ao processar video",
                            corpo));
        });

    }

}
