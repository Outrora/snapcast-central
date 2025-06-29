package br.com.snapcast.ports.event;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.StatusVideo;
import br.com.snapcast.domain.user_cases.AtualizarVideoUserCase;
import br.com.snapcast.ports.event.consumer.AtulizacaoEstadoConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class AtulizacaoEstadoConsumerTest {

    @Mock
    AtualizarVideoUserCase userCase;

    AtulizacaoEstadoConsumer consumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        consumer = new AtulizacaoEstadoConsumer(userCase);
    }

    @Test
    void testarSeAoReceberVideo() {
        var evento = new StatusVideo(UUID.randomUUID().toString(),
                StatusProcessamento.EM_PROCESSAMENTO, 10);

        doNothing().when(userCase).atulizarEstado(evento);

        consumer.receberVideo(evento);

        verify(userCase, times(1)).atulizarEstado(evento);
    }

    @Test
    void testarSeAoFallbackReceberVideo() {
        var evento = new StatusVideo(UUID.randomUUID().toString(),
                StatusProcessamento.EM_PROCESSAMENTO, 10);

        doNothing().when(userCase).atulizarEstado(evento);

        consumer.fallbackReceberVideo(evento);

        verify(userCase, times(0)).atulizarEstado(evento);
    }
}
