package br.com.snapcast.domain.user_cases;

import java.net.URL;
import java.util.UUID;

import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.ports.s3.GeradorDeLink;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import br.com.snapcast.shared.exception.ZipNaoEncontrado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class GerarLinkUserCase {

    GeradorDeLink link;
    VideoDatabase database;

    public URL criarLinkTemporario(UUID id) {

        var video = database.buscarPeloId(id).orElseThrow(VideoNaoCadastrado::new);

        if (!video.getStatus().exiteArquivoZip())
            throw new ZipNaoEncontrado(id.toString());

        return link.criarLinkTemporario(video.arquivoZip());

    }

}
