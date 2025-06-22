package br.com.snapcast.domain.entity;

import java.util.List;

public enum StatusProcessamento {
    FALHA(List.of(), false),
    CONCLUIDO(List.of(), true),
    EM_PROCESSAMENTO(List.of(CONCLUIDO, FALHA), false),
    NAO_INICIADO(List.of(FALHA, CONCLUIDO, EM_PROCESSAMENTO), false);

    private List<StatusProcessamento> proximasEtapas;
    private Boolean eZip;

    StatusProcessamento(List<StatusProcessamento> proximasEtapas, Boolean eZip) {
        this.proximasEtapas = proximasEtapas;
        this.eZip = eZip;
    }

    public Boolean ePossivelProximoEstagio(StatusProcessamento proximo) {
        return proximasEtapas.contains(proximo);
    }

    public Boolean exiteArquivoZip() {
        return eZip;
    }
}
