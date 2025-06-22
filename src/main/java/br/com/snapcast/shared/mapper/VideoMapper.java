package br.com.snapcast.shared.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.StatusVideo;
import br.com.snapcast.domain.entity.StatusVideoId;
import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.ports.database.dto.VideoDTO;
import br.com.snapcast.ports.event.entity.VideoEvento;

public interface VideoMapper {

    public static Video toEntity(VideoEvento evento) {
        return Video.builder()
                .id(evento.id())
                .nome(evento.nome())
                .formatoVideo(evento.formatoVideo())
                .tamanhoVideo(evento.tamanhoVideo())
                .url(evento.caminhoVideo())
                .idUsuario(evento.idUsuario())
                .horaUpload(LocalDateTime.now())
                .status(StatusProcessamento.NAO_INICIADO)
                .build();
    }

    public static Video toEntity(VideoDTO dto) {
        return Video.builder()
                .id(dto.getId().toString())
                .nome(dto.getNome())
                .formatoVideo(dto.getFormatoVideo())
                .tamanhoVideo(dto.getTamanhoVideo())
                .idUsuario(dto.getIdUsuario())
                .url(dto.getUrl())
                .horaUpload(dto.getHoraUpload())
                .status(dto.getStatus())
                .build();
    }

    public static VideoDTO toDTO(Video video) {
        return VideoDTO.builder()
                .id(UUID.fromString(video.getId()))
                .nome(video.getNome())
                .formatoVideo(video.getFormatoVideo())
                .tamanhoVideo(video.getTamanhoVideo())
                .url(video.getUrl())
                .idUsuario(video.getIdUsuario())
                .status(video.getStatus())
                .horaUpload(video.getHoraUpload())
                .build();
    }

}
