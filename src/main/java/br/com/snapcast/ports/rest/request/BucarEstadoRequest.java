package br.com.snapcast.ports.rest.request;

import br.com.snapcast.domain.entity.StatusProcessamento;

public record BucarEstadoRequest(
        StatusProcessamento status) {

}
