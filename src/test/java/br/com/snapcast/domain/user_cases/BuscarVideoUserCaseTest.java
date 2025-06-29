package br.com.snapcast.domain.user_cases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;

class BuscarVideoUserCaseTest {

    @Mock
    VideoDatabase database;

    BuscarVideoUserCase userCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userCase = new BuscarVideoUserCase(database);
    }

    @Test
    void seBuscarTodosVideoFuncionaCorretamente() {
        var lista = criarVideos.criarLista();

        when(database.buscarTodos()).thenReturn(lista);

        var resposta = userCase.buscarTodosVideos();

        assertThat(resposta)
                .isSameAs(lista)
                .isNotEmpty()
                .hasSize(lista.size());
    }

    @Test
    void seBuscarTodosVideoFuncionaCorretamentePeloStatus() {
        var lista = criarVideos.criarLista();

        when(database.buscarPeloEstado(any())).thenReturn(lista);

        var resposta = userCase.buscarPeloStatus(StatusProcessamento.CONCLUIDO);

        assertThat(resposta)
                .isSameAs(lista)
                .isNotEmpty()
                .hasSize(lista.size());

        verify(database, times(1)).buscarPeloEstado(StatusProcessamento.CONCLUIDO);
    }

    @Test
    void seBuscarTodosVideoFuncionaCorretamentePeloId() {
        var video = criarVideos.criar();

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));

        var resposta = userCase.buscarPeloId(video.getId());

        assertThat(resposta)
                .isEqualTo(video);

        verify(database, times(1))
                .buscarPeloId(UUID.fromString(video.getId()));
    }

    @Test
    void seBuscarTodosVideoFuncionaRetornaErroPeloId() {
        var video = criarVideos.criar();

        when(database.buscarPeloId(any())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            userCase.buscarPeloId(video.getId());
        })
                .isInstanceOf(VideoNaoCadastrado.class)
                .hasMessageContaining("Video nao foi encontrado");

        verify(database, times(1))
                .buscarPeloId(UUID.fromString(video.getId()));
    }

}
