package br.com.snapcast.domain.listener;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.StatusVideo;
import br.com.snapcast.domain.entity.StatusVideoId;
import br.com.snapcast.ports.s3.PegarEmail;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class StatusVideoListener {

    PegarEmail pegarEmail;

    public void enviarEmail(@Observes(during = TransactionPhase.AFTER_SUCCESS) StatusVideoId status) {
        log.info("Status do video %s alterado com sucesso".formatted(status.id()));
        if (status.processamento() == StatusProcessamento.FALHA) {
            var email = pegarEmail.pegarEmailAtravesUsername(status.idUsuario());
            log.info("Enviar Email: %s".formatted(email));
        }
    }

}
