package br.com.snapcast.domain.user_cases;

import java.util.UUID;

import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.VideoCadastrado;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class InserirVideoUserCase {

    VideoDatabase database;

    @Transactional
    public void inserirVideo(Video video) throws VideoCadastrado {
        log.info("🔗 Inserindo na Base de Dados video com ID: %S".formatted(video.getNome()));
        verificarSeJaEstarCadastrado(video);
        database.inserirVideo(video);

    }

    private void verificarSeJaEstarCadastrado(Video video) throws VideoCadastrado {
        database.buscarPeloId(UUID.fromString(video.getId()))
                .ifPresent(v -> {
                    throw new VideoCadastrado();
                });

    }

}
