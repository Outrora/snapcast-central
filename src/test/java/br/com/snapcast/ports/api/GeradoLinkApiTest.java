package br.com.snapcast.ports.api;

import br.com.snapcast.domain.user_cases.GerarLinkUserCase;
import br.com.snapcast.ports.rest.api.zip.GeradoLinkApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GeradoLinkApiTest {

    @Mock
    GerarLinkUserCase userCase;

    GeradoLinkApi api;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        api = new GeradoLinkApi(userCase);
    }

    @Test
    void verificarSeGeradorLinkApi() throws MalformedURLException {


        var id = UUID.randomUUID().toString();

        when(userCase.criarLinkTemporario(any(UUID.class)))
                .thenReturn(new URL("http://example.com/download.zip"));

        var url = api.linkTemporario(id);

        assertThat(url).isNotNull();

        verify(userCase, times(1)).criarLinkTemporario(any(UUID.class));
    }
}
