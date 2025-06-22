package br.com.snapcast.ports.event.consumer;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import br.com.snapcast.domain.user_cases.InserirVideoUserCase;
import br.com.snapcast.ports.event.entity.VideoEvento;
import br.com.snapcast.shared.exception.VideoCadastrado;
import br.com.snapcast.shared.mapper.VideoMapper;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class ArquivoSalvaConsumer {

    InserirVideoUserCase userCase;

    @RunOnVirtualThread
    @Incoming("video-uploads")
    @Retry(delay = 10, maxRetries = 2)
    @Transactional
    public void receberVideo(VideoEvento evento) throws VideoCadastrado {
        log.info("🛬 Recebendo video para salva no banco de dados: %s".formatted(evento.id()));
        var video = VideoMapper.toEntity(evento);
        userCase.inserirVideo(video);
    }

}
