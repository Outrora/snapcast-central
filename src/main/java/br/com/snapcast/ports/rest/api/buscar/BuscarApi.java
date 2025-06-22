package br.com.snapcast.ports.rest.api.buscar;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.domain.user_cases.BuscarVideoUserCase;
import br.com.snapcast.ports.rest.request.BucarEstadoRequest;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Path("buscar")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
@Tag(name = "Busca", description = "Endpoints busca dos videos")
public class BuscarApi {

    BuscarVideoUserCase userCase;

    @GET
    @RunOnVirtualThread
    @Authenticated
    @Operation(summary = "Buscar todos os video a ser processado")
    public List<Video> buscarTodosOsVideos() {
        return userCase.buscarTodosVideos();
    }

    @GET()
    @Path("{id}")
    @RunOnVirtualThread
    @Operation(summary = "Busca  o video a ser processado")
    public Video buscarPeloId(@PathParam("id") String id) {
        return userCase.buscarPeloId(id);
    }

    @POST()
    @RunOnVirtualThread
    @Operation(summary = "Buscar todos os video de acordo com o estado")
    public List<Video> buscarPeloId(BucarEstadoRequest request) {
        return userCase.buscarPeloStatus(request.status());
    }

}
