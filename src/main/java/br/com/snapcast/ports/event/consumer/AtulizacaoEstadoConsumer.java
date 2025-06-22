package br.com.snapcast.ports.event.consumer;

import java.util.UUID;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import br.com.snapcast.domain.user_cases.AtualizarVideoUserCase;
import br.com.snapcast.ports.event.entity.StatusVideo;
import br.com.snapcast.shared.exception.VideoCadastrado;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class AtulizacaoEstadoConsumer {

    AtualizarVideoUserCase userCase;

    @RunOnVirtualThread
    @Incoming("video-status")
    @Retry(delay = 10, maxRetries = 2)
    @Transactional
    public void receberVideo(StatusVideo evento) throws VideoCadastrado {
        log.info("🛬 Recebendo atualização video para salva no banco de dados: %s".formatted(evento.id()));
        userCase.atulizarEstado(UUID.fromString(evento.id()), evento.processamento(),
                evento.quantidadeFrames());
    }
}
