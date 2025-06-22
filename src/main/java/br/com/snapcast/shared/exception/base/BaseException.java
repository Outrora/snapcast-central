package br.com.snapcast.shared.exception.base;

import java.util.Arrays;
import java.util.logging.Level;

import jakarta.ws.rs.core.Response.Status;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public abstract class BaseException extends RuntimeException {

    private final Status status;
    private final String mensagemErro;

    protected BaseException(Status status, String mensagemErro) {
        this(status, mensagemErro, null);
    }

    protected BaseException(Status status, String mensagemErro, Throwable causa) {
        super(mensagemErro, causa);
        this.status = status;
        this.mensagemErro = mensagemErro;

        logError();
        logStackTrace();
    }

    private void logError() {
        String mensagem = String.format("🛑 Erro: %s | Classe: %s ",
                mensagemErro,
                getClass().getSimpleName());

        log.log(Level.SEVERE, mensagem);
    }

    private void logStackTrace() {
        String stackTrace = Arrays.stream(getStackTrace())
                .map(StackTraceElement::toString)
                .reduce("", (acc, element) -> acc + "\n\t" + element);

        log.log(Level.INFO, "🔍 Stack Trace:{0}", stackTrace);
    }

}