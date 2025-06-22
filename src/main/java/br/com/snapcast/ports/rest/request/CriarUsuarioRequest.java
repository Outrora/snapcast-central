package br.com.snapcast.ports.rest.request;

public record CriarUsuarioRequest(

                String email,
                String senha) {
}