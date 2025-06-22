package br.com.snapcast.ports.database.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;

public interface VideoDatabase {
    public List<Video> buscarTodos();

    public List<Video> buscarPeloEstado(StatusProcessamento status);

    public Optional<Video> buscarPeloId(UUID id);

    public void alterarStatus(UUID id, StatusProcessamento status, Integer quantidadeFrames) throws VideoNaoCadastrado;

    public void inserirVideo(Video video);
}