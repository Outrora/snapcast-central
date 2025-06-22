package br.com.snapcast.ports.event.entity;

public record VideoEvento(
        String id,
        String nome,
        String formatoVideo,
        Long tamanhoVideo,
        String caminhoVideo) {
}