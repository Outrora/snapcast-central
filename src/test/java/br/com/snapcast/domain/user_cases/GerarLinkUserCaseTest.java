package br.com.snapcast.domain.user_cases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.ports.adapter.cloud.GeradorDeLinks;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import br.com.snapcast.shared.exception.ZipNaoEncontrado;

class GerarLinkUserCaseTest {

    @Mock
    GeradorDeLinks link;

    @Mock
    VideoDatabase database;

    GerarLinkUserCase userCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userCase = new GerarLinkUserCase(link, database);

    }

    @Test
    void seCrieUmLinkTemporarioCorretamente() throws MalformedURLException {

        var video = criarVideos.criar(StatusProcessamento.CONCLUIDO);
        URL url = new URL("http:wwww.com.br");

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));

        when(link.criarLinkTemporario(anyString())).thenReturn(url);

        var returno = userCase.criarLinkTemporario(UUID.randomUUID());

        assertThat(returno).isEqualTo(url);

        verify(database, times(1)).buscarPeloId(any());
        verify(link, times(1)).criarLinkTemporario(anyString());

    }

    @Test
    void seCrieUmLinkTemporarioEDeuErroZIP() throws MalformedURLException {

        var video = criarVideos.criar(StatusProcessamento.FALHA);
        URL url = new URL("http:wwww.com.br");

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));

        when(link.criarLinkTemporario(anyString())).thenReturn(url);

        assertThatThrownBy(() -> {
            userCase.criarLinkTemporario(UUID.randomUUID());
        }).isInstanceOf(ZipNaoEncontrado.class);

        verify(database, times(1)).buscarPeloId(any());
        verify(link, times(0)).criarLinkTemporario(anyString());

    }

    @Test
    void seCrieUmLinkTemporarioEDeuErroVideoNaoCadastrado() throws MalformedURLException {

        URL url = new URL("http:wwww.com.br");

        when(database.buscarPeloId(any())).thenReturn(Optional.empty());

        when(link.criarLinkTemporario(anyString())).thenReturn(url);

        assertThatThrownBy(() -> {
            userCase.criarLinkTemporario(UUID.randomUUID());
        }).isInstanceOf(VideoNaoCadastrado.class);

        verify(database, times(1)).buscarPeloId(any());
        verify(link, times(0)).criarLinkTemporario(anyString());

    }
}
