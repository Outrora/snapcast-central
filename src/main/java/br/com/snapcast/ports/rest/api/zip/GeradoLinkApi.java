package br.com.snapcast.ports.rest.api.zip;

import java.net.URI;
import java.net.URL;
import java.util.UUID;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.snapcast.domain.user_cases.GerarLinkUserCase;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Path("link")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
@Tag(name = "Link", description = "Endpoints relacionado para a geração de links para download")
public class GeradoLinkApi {

    GerarLinkUserCase userCase;

    @GET
    @Path("{id}")
    @RunOnVirtualThread
    @Authenticated
    @Operation(summary = "Para criar um link temporário com o arquivo ZIP com os frames")
    public URL linkTemporario(@PathParam("id") String id) {
        return userCase.criarLinkTemporario(UUID.fromString(id));
    }

}
