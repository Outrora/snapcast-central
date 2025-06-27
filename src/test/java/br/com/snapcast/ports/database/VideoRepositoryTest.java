package br.com.snapcast.ports.database;

import br.com.snapcast.criar_objetos.criarVideos;
import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.ports.database.dto.VideoDTO;
import br.com.snapcast.ports.database.repository.VideoRepository;
import br.com.snapcast.shared.exception.VideoCadastrado;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class VideoRepositoryTest {

    @Mock
    VideoRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void seBuscarTodosEstaFuncionadoCorretamente() {
        var lista = criarVideos.criarListaDTO();

        when(repository.buscarTodos()).thenCallRealMethod();
        when(repository.listAll()).thenReturn(lista);

        List<Video> resultado = repository.buscarTodos();

        assertThat(resultado)
                .isNotNull()
                .isInstanceOf(List.class);

        assertThat(resultado.size())
                .isEqualTo(lista.size());

        verify(repository, times(1)).listAll();
    }

    @Test
    void seBuscarPeloEstadoEstaFuncionandoCorretamente() {
        var lista = criarVideos.criarListaDTO();

        when(repository.buscarPeloEstado(any())).thenCallRealMethod();
        when(repository.list(anyString(), any(StatusProcessamento.class))).thenReturn(lista);

        List<Video> resultado = repository.buscarPeloEstado(StatusProcessamento.EM_PROCESSAMENTO);

        assertThat(resultado)
                .isNotNull()
                .isInstanceOf(List.class);

        assertThat(resultado.size())
                .isEqualTo(lista.size());

        verify(repository, times(1)).list(anyString(), any(StatusProcessamento.class));
    }

    @Test
    void seBuscarPeloIdEstaFuncionandoCorretamente() {
        var video = criarVideos.criarDTO();
        PanacheQuery<VideoDTO> dto = mock(PanacheQuery.class);
        when(dto.singleResultOptional()).thenReturn(java.util.Optional.of(video));
        when(repository.find(anyString(), any(UUID.class))).thenReturn(dto);


        when(repository.buscarPeloId(any())).thenCallRealMethod();
        when(repository.find(anyString(), any(UUID.class))).thenReturn(dto);

        var resultado = repository.buscarPeloId(video.getId());

        assertThat(resultado)
                .isNotNull()
                .isInstanceOf(Optional.class)
                .isNotEmpty()
                .isPresent();

        verify(repository, times(1)).find(anyString(), any(UUID.class));
    }

    @Test
    void seInserirVideoEstaFuncionandoCorretamente() {
        var video = criarVideos.criar();

        doCallRealMethod().when(repository).inserirVideo(any(Video.class));
        doNothing().when(repository).persist(any(VideoDTO.class));

        repository.inserirVideo(video);

        verify(repository, times(1)).persist(any(VideoDTO.class));
    }

    @Test
    void seAlterarStatusEstaFuncionandoCorretamente() {
        var video = criarVideos.criarDTO();
        var status = StatusProcessamento.EM_PROCESSAMENTO;
        var quantidadeFrames = 100;

        PanacheQuery<VideoDTO> dto = mock(PanacheQuery.class);

        doCallRealMethod().when(repository).alterarStatus(any(UUID.class), any(StatusProcessamento.class), anyInt());
        when(repository.find(anyString(), any(UUID.class))).thenReturn(dto);
        when(dto.singleResultOptional()).thenReturn(Optional.of(video));
        doNothing().when(repository).persist(any(VideoDTO.class));

        repository.alterarStatus(video.getId(), status, quantidadeFrames);

        verify(repository, times(1)).find(anyString(), any(UUID.class));
        verify(repository, times(1)).persist(any(VideoDTO.class));
    }

    @Test
    void seAlterarStatusEstaFuncionandoRetonarOErroCorretamente() {
        var video = criarVideos.criar();
        var status = StatusProcessamento.EM_PROCESSAMENTO;
        var quantidadeFrames = 100;

        doCallRealMethod().when(repository).alterarStatus(any(UUID.class), any(StatusProcessamento.class), anyInt());
        when(repository.find(anyString(), any(UUID.class))).thenReturn(mock(PanacheQuery.class));
        doNothing().when(repository).persist(any(VideoDTO.class));

        assertThatThrownBy(() -> {
            repository.alterarStatus(UUID.fromString(video.getId()), status, quantidadeFrames);
        })
                .hasMessageContaining("❌")
                .isInstanceOf(VideoCadastrado.class);

        verify(repository, times(1)).find(anyString(), any(UUID.class));
        verify(repository, times(0)).persist(any(VideoDTO.class));
    }

}
