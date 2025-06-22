package br.com.snapcast.ports.rest.api.login;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;

import br.com.snapcast.domain.entity.Video;
import br.com.snapcast.domain.user_cases.LoginUserCase;
import br.com.snapcast.ports.rest.request.BucarEstadoRequest;
import br.com.snapcast.ports.rest.request.CriarUsuarioRequest;
import br.com.snapcast.ports.rest.request.LoginRequest;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Path("login")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor(onConstructor = @__(@Inject))
@Log
@Tag(name = "Login", description = "Endpoints relacionado a login")
public class LoginApi {

    LoginUserCase userCase;

    @POST()
    @RunOnVirtualThread
    @Operation(summary = "Para Realizar o Login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(LoginRequest request) {
        return userCase.authenticate(request.email(), request.senha());
    }

    @Path("criar")
    @POST()
    @RunOnVirtualThread
    @Operation(summary = "Para criar novo usuario")
    public Response criarUsuario(CriarUsuarioRequest request) {
        userCase.criarUsuario(request.email(), request.senha());

        return Response
                .status(Status.ACCEPTED)
                .build();
    }

}
