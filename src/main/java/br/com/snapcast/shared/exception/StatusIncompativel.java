package br.com.snapcast.shared.exception;

import java.util.logging.Level;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.shared.exception.base.BaseException;
import lombok.extern.java.Log;

import jakarta.ws.rs.core.Response.Status;

@Log
public class StatusIncompativel extends BaseException {

    private final static String MENSAGEM = "❌ Video: %s não pode ser alterado para o estado : %s";

    public StatusIncompativel(String id, StatusProcessamento status) {
        super(Status.BAD_REQUEST, MENSAGEM.formatted(id, status.toString()));
        log.log(Level.SEVERE, MENSAGEM.formatted(id, status.toString()));
    }

}
