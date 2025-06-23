package br.com.snapcast.domain.user_cases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.awaitility.Awaitility.await;

import br.com.snapcast.criar_objetos.criarVideos;
import io.quarkus.mailer.Mailer;

class EnviarEmailUserCaseTest {

    @Mock
    Mailer mailer;

    EnviarEmailUserCase userCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userCase = new EnviarEmailUserCase(mailer);
    }

    @Test
    void enviarEmailCorretamente() {
        var status = criarVideos.criarVideoId();

        doNothing().when(mailer).send(any());
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .until(() -> {
                    userCase.enviarEmailErro(status, "teste@gmail.com");
                    return true;
                });

        verify(mailer, times(1)).send(any());
    }

}
