package br.com.snapcast.domain.user_cases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import br.com.snapcast.domain.entity.StatusVideoId;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.StatusIncompativel;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import jakarta.enterprise.event.Event;

class AtualizarVideoUserCaseTest {

    @Mock
    VideoDatabase database;

    @Mock
    Event<StatusVideoId> event;

    AtualizarVideoUserCase userCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userCase = new AtualizarVideoUserCase(database, event);
    }

    @Test
    void seAtulizarEstadoFuncionaCorretamente() {
        var status = criarVideos.criarStatus(StatusProcessamento.EM_PROCESSAMENTO);
        var video = criarVideos.criar(StatusProcessamento.NAO_INICIADO);

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));
        doNothing().when(event).fire(any());

        userCase.atulizarEstado(status);

        verify(database, times(1))
                .alterarStatus(UUID.fromString(status.id()), status.processamento(), status.quantidadeFrames());
        verify(event, times(1)).fire(any());

    }

    @Test
    void seAtulizarEstadoRetornaErroNaoEncontraVideo() {
        var status = criarVideos.criarStatus(StatusProcessamento.EM_PROCESSAMENTO);
        // var video = criarVideos.criar(StatusProcessamento.NAO_INICIADO);

        when(database.buscarPeloId(any())).thenReturn(Optional.empty());
        doNothing().when(event).fire(any());

        assertThatThrownBy(() -> {
            userCase.atulizarEstado(status);
        })
                .isInstanceOf(VideoNaoCadastrado.class)
                .hasMessageContaining("Video nao foi encontrado  na base de dados");

        verify(database, times(0))
                .alterarStatus(UUID.fromString(status.id()), status.processamento(), status.quantidadeFrames());
        verify(event, times(0)).fire(any());

    }

    @Test
    void seAtulizarEstadoRetornaErroNaoPodeAtulizar() {
        var status = criarVideos.criarStatus(StatusProcessamento.EM_PROCESSAMENTO);
        var video = criarVideos.criar(StatusProcessamento.CONCLUIDO);

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));
        doNothing().when(event).fire(any());

        assertThatThrownBy(() -> {
            userCase.atulizarEstado(status);
        })
                .isInstanceOf(StatusIncompativel.class)
                .hasMessageContaining("não pode ser alterado para o estado")
                .hasMessageContaining(status.id());

        verify(database, times(0))
                .alterarStatus(UUID.fromString(status.id()), status.processamento(), status.quantidadeFrames());
        verify(event, times(0)).fire(any());

    }

    @Test
    void seAtulizarEstadoRetornaSemFazerNada() {
        var status = criarVideos.criarStatus(StatusProcessamento.EM_PROCESSAMENTO);
        var video = criarVideos.criar(StatusProcessamento.EM_PROCESSAMENTO);

        when(database.buscarPeloId(any())).thenReturn(Optional.of(video));
        doNothing().when(event).fire(any());

        userCase.atulizarEstado(status);

        verify(database, times(0))
                .alterarStatus(UUID.fromString(status.id()), status.processamento(), status.quantidadeFrames());
        verify(event, times(0)).fire(any());

    }

}
