package br.com.snapcast.shared.exception;

import br.com.snapcast.shared.exception.base.BaseException;
import jakarta.ws.rs.core.Response.Status;

public class UsuarioNaoCadastro extends BaseException {

    private final static String MENSAGEM = "❌ Usuário nao encontrado na Base de Dados";

    public UsuarioNaoCadastro() {
        super(Status.BAD_REQUEST, MENSAGEM);

    }

}
