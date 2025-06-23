package br.com.snapcast.criar_objetos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.StatusVideo;
import br.com.snapcast.domain.entity.StatusVideoId;
import br.com.snapcast.domain.entity.Video;

public interface criarVideos {
    static String[] formatos = { "mp4", "avi", "mov" };
    static String[] nomes = { "videoAula", "tutorial", "entrevista", "demo" };

    static Video criar() {

        StatusProcessamento status = StatusProcessamento.values()[ThreadLocalRandom.current()
                .nextInt(StatusProcessamento.values().length)];

        return criar(status);

    }

    static List<Video> criarLista() {
        return IntStream.range(2, 10)
                .mapToObj(i -> criar())
                .toList();
    }

    static Video criar(StatusProcessamento status) {
        String id = UUID.randomUUID().toString();
        String nome = nomes[ThreadLocalRandom.current().nextInt(nomes.length)];
        String formatoVideo = formatos[ThreadLocalRandom.current().nextInt(formatos.length)];
        Long tamanhoVideo = ThreadLocalRandom.current().nextLong(10_000_000, 500_000_000); // entre 10MB e 500MB
        String url = "https://videos.exemplo.com/" + id;
        LocalDateTime horaUpload = LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(0, 30));
        String idUsuario = UUID.randomUUID().toString();

        return Video.builder()
                .id(id)
                .nome(nome)
                .formatoVideo(formatoVideo)
                .tamanhoVideo(tamanhoVideo)
                .url(url)
                .status(status)
                .horaUpload(horaUpload)
                .idUsuario(idUsuario)
                .build();
    }

    static StatusVideo criarStatus() {

        StatusProcessamento status = StatusProcessamento.values()[ThreadLocalRandom.current()
                .nextInt(StatusProcessamento.values().length)];

        return criarStatus(status);
    }

    static StatusVideo criarStatus(StatusProcessamento processamento) {
        String id = UUID.randomUUID().toString();
        int frames = ThreadLocalRandom.current().nextInt(10, 500);

        return new StatusVideo(id, processamento, frames);
    }

    static StatusVideoId criarVideoId() {
        String id = UUID.randomUUID().toString();
        return criarStatus().comId(id);
    }

    static StatusVideoId criarVideoId(StatusProcessamento processamento) {
        String id = UUID.randomUUID().toString();
        return criarStatus(processamento).comId(id);
    }
}
