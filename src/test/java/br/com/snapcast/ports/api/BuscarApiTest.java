package br.com.snapcast.ports.api;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.user_cases.BuscarVideoUserCase;
import br.com.snapcast.ports.rest.api.buscar.BuscarApi;
import br.com.snapcast.ports.rest.request.BucarEstadoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuscarApiTest {

    @Mock
    BuscarVideoUserCase userCase;

    BuscarApi api;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        api = new BuscarApi(userCase);
    }

    @Test
    void corretamenteQuandoBuscarTodosOsVideos() {
        var videos = criarVideos.criarLista();
        when(userCase.buscarTodosVideos()).thenReturn(videos);

        var resposta = api.buscarTodosOsVideos();

        assertThat(resposta)
                .isNotNull()
                .isNotEmpty()
                .hasSize(videos.size())
                .containsExactlyInAnyOrderElementsOf(videos);

    }

    @Test
    void corretamenteQuandoBuscarPeloId() {
        var video = criarVideos.criar();
        when(userCase.buscarPeloId(video.getId())).thenReturn(video);

        var resposta = api.buscarPeloId(video.getId());

        assertThat(resposta)
                .isNotNull()
                .isEqualTo(video);
    }

    @Test
    void buscarCorretamentePeloEstado() {
        var videos = criarVideos.criarLista();
        var request = new BucarEstadoRequest(StatusProcessamento.EM_PROCESSAMENTO);
        when(userCase.buscarPeloStatus(request.status())).thenReturn(videos);

        var resposta = api.buscarPeloEstado(request);

        assertThat(resposta)
                .isNotNull()
                .isNotEmpty()
                .hasSize(videos.size())
                .containsExactlyInAnyOrderElementsOf(videos);
    }


}
