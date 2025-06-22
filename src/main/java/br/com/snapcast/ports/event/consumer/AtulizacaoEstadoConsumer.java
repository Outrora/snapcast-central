package br.com.snapcast.ports.event.consumer;

import java.util.UUID;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import br.com.snapcast.domain.entity.StatusVideo;
import br.com.snapcast.domain.user_cases.AtualizarVideoUserCase;
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
    @Retry(delay = 100, maxRetries = 2)
    @Fallback(fallbackMethod = "fallbackReceberVideo")
    @Transactional
    public void receberVideo(StatusVideo evento) throws VideoCadastrado {
        log.info("🛬 Recebendo atualização video para salva no banco de dados: %s".formatted(evento.id()));
        userCase.atulizarEstado(evento);
    }

    public void fallbackReceberVideo(StatusVideo evento) {
        log.warning("⚠️ Falha ao processar evento após tentativas: %s".formatted(evento.id()));
    }
}
