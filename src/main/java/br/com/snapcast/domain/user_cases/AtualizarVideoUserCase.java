package br.com.snapcast.domain.user_cases;

import java.util.UUID;

import br.com.snapcast.domain.entity.StatusProcessamento;
import br.com.snapcast.ports.database.interfaces.VideoDatabase;
import br.com.snapcast.shared.exception.StatusIncompativel;
import br.com.snapcast.shared.exception.VideoNaoCadastrado;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
public class AtualizarVideoUserCase {

    VideoDatabase database;

    public void atulizarEstado(UUID id, StatusProcessamento statusProcessamento, Integer quantitadeFrames)
            throws VideoNaoCadastrado, StatusIncompativel {

        log.info("📀 Atualizando Estado de Processamento");

        var statusAntigo = database.buscarPeloId(id)
                .orElseThrow(VideoNaoCadastrado::new)
                .getStatus();

        if (!statusAntigo.ePossivelProximoEstagio(statusProcessamento)) {
            throw new StatusIncompativel(id.toString(), statusProcessamento);
        }
        database.alterarStatus(id, statusProcessamento, quantitadeFrames);
    }

}
