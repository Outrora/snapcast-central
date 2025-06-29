package br.com.snapcast.domain.user_cases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.VideoCadastrado;

class InserirVideoUserCaseTest {

    @Mock
    VideoDatabase database;

    InserirVideoUserCase userCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userCase = new InserirVideoUserCase(database);

    }

    @Test
    void verificarSeInserirVideoCorretamente() {
        var video = criarVideos.criar();

        when(database.buscarPeloId(any())).thenReturn(Optional.empty());
        doNothing().when(database).inserirVideo(any());

        userCase.inserirVideo(video);

        verify(database, times(1)).inserirVideo(video);
        verify(database, times(1)).buscarPeloId(any());

    }

    @Test
    void verificarNaoInserirVideoSeExite() {
        var video = criarVideos.criar();

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));
        doNothing().when(database).inserirVideo(any());

        assertThatThrownBy(() -> {
            userCase.inserirVideo(video);
        }).isInstanceOf(VideoCadastrado.class);

        verify(database, times(0)).inserirVideo(any());
        verify(database, times(1)).buscarPeloId(any());

    }

}
