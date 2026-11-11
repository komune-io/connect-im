package io.komune.im.infra.keycloak.client

import io.komune.im.keycloak.plugin.domain.model.KeycloakPluginIds
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType

internal interface WelcomeEmailResource {
    @Path("admin/realms/{realm}/${KeycloakPluginIds.WELCOME_EMAIL}/users/{id}")
    fun user(
        @PathParam("realm") realm: String,
        @PathParam("id") id: String,
    ): WelcomeEmailUserResource
}

internal interface WelcomeEmailUserResource {
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun send(
        @QueryParam("client_id") clientId: String?,
        @QueryParam("redirect_uri") redirectUri: String?,
        @QueryParam("lifespan") lifespan: Int,
        actions: List<String>
    )
}
