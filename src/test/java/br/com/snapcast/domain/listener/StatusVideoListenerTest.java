package br.com.snapcast.domain.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.user_cases.EnviarEmailUserCase;
import br.com.snapcast.ports.adapter.cloud.PegarEmail;

class StatusVideoListenerTest {

    @Mock
    PegarEmail pegarEmail;
    @Mock
    EnviarEmailUserCase enviarEmailUserCase;

    StatusVideoListener listener;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        listener = new StatusVideoListener(pegarEmail, enviarEmailUserCase);

    }

    @Test
    void testarSeObservarQuandoFalhaMandaEmail() {
        var video = criarVideos.criarVideoId(StatusProcessamento.FALHA);

        when(pegarEmail.pegarEmailAtravesUsername(anyString())).thenReturn(video.idUsuario());
        doNothing().when(enviarEmailUserCase).enviarEmailErro(any(), any());

        listener.observarStatus(video);

        verify(pegarEmail, times(1)).pegarEmailAtravesUsername(video.idUsuario());
        verify(enviarEmailUserCase, times(1)).enviarEmailErro(video, video.idUsuario());

    }

    @Test
    void testarSeObservarQuandoNaoFalhaMandaEmail() {
        var video = criarVideos.criarVideoId(StatusProcessamento.CONCLUIDO);

        when(pegarEmail.pegarEmailAtravesUsername(anyString())).thenReturn(video.idUsuario());
        doNothing().when(enviarEmailUserCase).enviarEmailErro(any(), any());

        listener.observarStatus(video);

        verify(pegarEmail, times(0)).pegarEmailAtravesUsername(video.idUsuario());
        verify(enviarEmailUserCase, times(0)).enviarEmailErro(video, video.idUsuario());

    }

}
