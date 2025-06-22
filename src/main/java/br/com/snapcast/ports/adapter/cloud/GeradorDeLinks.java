package br.com.snapcast.ports.adapter.cloud;

import java.net.URL;

public interface GeradorDeLinks {
    URL criarLinkTemporario(String key);
}