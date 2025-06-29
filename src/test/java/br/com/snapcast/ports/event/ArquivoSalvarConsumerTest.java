package br.com.snapcast.ports.event;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.domain.user_cases.InserirVideoUserCase;
import br.com.snapcast.ports.event.consumer.ArquivoSalvaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArquivoSalvarConsumerTest {

    @Mock
    InserirVideoUserCase userCase;

    ArquivoSalvaConsumer consumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        consumer = new ArquivoSalvaConsumer(userCase);
    }

    @Test
    void testReceberVideo() {

        var evento = criarVideos.evento();

        doNothing().when(userCase).inserirVideo(any());

        consumer.receberVideo(evento);

        verify(userCase, times(1)).inserirVideo(any());

    }
}
