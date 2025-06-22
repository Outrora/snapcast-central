package br.com.snapcast.ports.database.dto;

import java.time.LocalDateTime;

import java.util.UUID;

import br.com.snapcast.domain.entity.StatusProcessamento;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@ApplicationScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "video")
public class VideoDTO {

    @Id
    private UUID id;
    private String nome;
    private String formatoVideo;
    private Long tamanhoVideo;
    private String url;
    private StatusProcessamento status;
    private LocalDateTime horaUpload;
    private int quandidadeFrames;

}
