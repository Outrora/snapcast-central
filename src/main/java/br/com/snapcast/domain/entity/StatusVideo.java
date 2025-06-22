package br.com.snapcast.domain.entity;

public record StatusVideo(String id, StatusProcessamento processamento, Integer quantidadeFrames) {

    public StatusVideoId comId(String idUsuario) {
        return new StatusVideoId(id, processamento, idUsuario);
    }

};
