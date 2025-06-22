package br.com.snapcast.shared.exception;

import java.util.logging.Level;

import br.com.snapcast.shared.exception.base.BaseException;
import lombok.extern.java.Log;

import jakarta.ws.rs.core.Response.Status;

@Log
public class ZipNaoEncontrado extends BaseException {

    private final static String MENSAGEM = "❌ O arquivo ZIP do video: %s não foi encontrado";

    public ZipNaoEncontrado(String id) {
        super(Status.BAD_REQUEST, MENSAGEM.formatted(id));
        log.log(Level.SEVERE, MENSAGEM.formatted(id));
    }

}
