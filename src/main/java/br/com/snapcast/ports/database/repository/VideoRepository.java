package br.com.snapcast.ports.database.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.ports.database.dto.VideoDTO;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.VideoCadastrado;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import br.com.snapcast.shared.mapper.VideoMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class VideoRepository implements PanacheRepository<VideoDTO>, VideoDatabase {
    @Override
    public List<Video> buscarTodos() {
        return listAll()
                .stream()
                .map(VideoMapper::toEntity)
                .toList();
    }

    @Override
    public List<Video> buscarPeloEstado(StatusProcessamento status) {
        return list("status", status).stream()
                .map(VideoMapper::toEntity)
                .toList();

    }

    @Override
    public Optional<Video> buscarPeloId(UUID id) {
        return find("id", id)
                .singleResultOptional()
                .map(VideoMapper::toEntity);
    }

    @Override
    @Transactional
    public void inserirVideo(Video video) {
        var dto = VideoMapper.toDTO(video);
        persist(dto);
    }

    @Override
    @Transactional
    public void alterarStatus(UUID id, StatusProcessamento status, Integer quantidadeFrames) throws VideoNaoCadastrado {
        var dto = find("id", id)
                .singleResultOptional()
                .orElseThrow(VideoCadastrado::new);

        dto.setStatus(status);
        dto.setQuandidadeFrames(quantidadeFrames);
        persist(dto);
    }

}
