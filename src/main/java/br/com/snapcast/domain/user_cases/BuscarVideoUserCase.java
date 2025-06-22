package br.com.snapcast.domain.user_cases;

import java.util.List;
import java.util.UUID;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class BuscarVideoUserCase {

    VideoDatabase database;

    public List<Video> buscarTodosVideos() {
        return database.buscarTodos();
    }

    public Video buscarPeloId(String id) {
        return database.buscarPeloId(UUID.fromString(id))
                .orElseThrow(VideoNaoCadastrado::new);
    }

    public List<Video> buscarPeloStatus(StatusProcessamento statusProcessamento) {
        return database.buscarPeloEstado(statusProcessamento);
    }
}
