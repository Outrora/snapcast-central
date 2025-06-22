package br.com.snapcast.domain.user_cases;

import java.util.UUID;

import br.com.snapcast.domain.entity.StatusVideo;
import br.com.snapcast.domain.entity.StatusVideoId;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.StatusIncompativel;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class AtualizarVideoUserCase {

    private final VideoDatabase database;

    private final Event<StatusVideoId> event;

    public void atulizarEstado(StatusVideo statusVideo)
            throws VideoNaoCadastrado, StatusIncompativel {

        UUID uuid = UUID.fromString(statusVideo.id());

        var dto = database.buscarPeloId(uuid)
                .orElseThrow(VideoNaoCadastrado::new);

        var statusAntigo = dto.getStatus();
        if (statusAntigo == statusVideo.processamento())
            return;

        log.info("📀 Atualizando Estado de Processamento");

        if (!statusAntigo.ePossivelProximoEstagio(statusVideo.processamento())) {
            throw new StatusIncompativel(statusVideo.id(), statusVideo.processamento());
        }
        database.alterarStatus(uuid, statusVideo.processamento(), statusVideo.quantidadeFrames());

        event.fire(statusVideo.comId(dto.getIdUsuario()));
    }

}
