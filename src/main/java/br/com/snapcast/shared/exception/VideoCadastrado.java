package br.com.snapcast.shared.exception;

import java.util.logging.Level;

import br.com.snapcast.shared.exception.base.BaseException;
import jakarta.ws.rs.core.Response.Status;
import lombok.extern.java.Log;

@Log
public class VideoCadastrado extends BaseException {

    private final static String MENSAGEM = "❌ Video já cadastrado na base de dados";

    public VideoCadastrado() {
        super(Status.BAD_REQUEST, MENSAGEM);
        log.log(Level.SEVERE, MENSAGEM);

    }

}
