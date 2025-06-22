package br.com.snapcast.ports.event.entity;

import br.com.snapcast.domain.entity.StatusProcessamento;

public record StatusVideo(String id, StatusProcessamento processamento, Integer quantidadeFrames) {
};
