package br.com.snapcast.domain.entity;

import java.net.URL;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Video {

    private String id;
    private String nome;
    private String formatoVideo;
    private Long tamanhoVideo;
    private String url;
    private StatusProcessamento status;
    private LocalDateTime horaUpload;

    public String nomeDoVideoOriginalComExtensao() {
        return nome + "." + formatoVideo;
    }

    public String nomeDoVideo() {
        return id + "." + formatoVideo;
    }

    public void alteraUrl(URL uri) {
        this.url = uri.toString();
    }
}
