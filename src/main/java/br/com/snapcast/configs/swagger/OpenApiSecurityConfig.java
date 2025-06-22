package br.com.snapcast.configs.swagger;

import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import jakarta.ws.rs.core.Application;

@SecurityScheme(securitySchemeName = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiSecurityConfig extends Application {

}
