package br.com.snapcast.domain.entity;

import java.util.List;

public enum StatusProcessamento {
    FALHA(List.of()),
    CONCLUIDO(List.of()),
    EM_PROCESSAMENTO(List.of(CONCLUIDO, FALHA)),
    NAO_INICIADO(List.of(FALHA, CONCLUIDO, EM_PROCESSAMENTO));

    private List<StatusProcessamento> proximasEtapas;

    StatusProcessamento(List<StatusProcessamento> proximasEtapas) {
        this.proximasEtapas = proximasEtapas;
    }

    public Boolean ePossivelProximoEstagio(StatusProcessamento proximo) {

        return proximasEtapas.contains(proximo);
    }
}
