package br.com.snapcast.ports.adapter.cloud;

public interface Login {

    public String authenticate(String email, String senha);

    public void criarUsuario(String email, String senha);

}
