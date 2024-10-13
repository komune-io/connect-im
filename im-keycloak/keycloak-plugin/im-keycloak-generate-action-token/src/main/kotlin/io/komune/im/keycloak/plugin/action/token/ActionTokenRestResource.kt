package io.komune.im.keycloak.plugin.action.token

import jakarta.ws.rs.ForbiddenException
import jakarta.ws.rs.GET
import jakarta.ws.rs.NotAuthorizedException
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import org.keycloak.authentication.actiontoken.execactions.ExecuteActionsActionToken
import org.keycloak.models.KeycloakSession
import org.keycloak.services.managers.AppAuthManager

class ActionTokenRestResource(
    private val session: KeycloakSession
) {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    fun getActionToken(
        @QueryParam("client_id") clientId: String,
        @QueryParam("redirect_uri") redirectUri: String?,
        @QueryParam("user_id") userId: String,
        @QueryParam("action") action: String,
        @QueryParam("lifespan") lifespan: Int,
    ): String {
        checkResourceAccess("realm-management", "manage-users", "Does not have permission to generate action token")
        return ExecuteActionsActionToken(
            userId,
            lifespan,
            listOf(action),
            redirectUri,
            clientId
        ).serialize(session, session.context.realm, session.context.uri)
    }

    private fun checkResourceAccess(client: String, role: String, message: String) {
        val auth = AppAuthManager.BearerTokenAuthenticator(session).authenticate()
            ?: throw NotAuthorizedException("Bearer")

        if (auth.token.resourceAccess[client]?.isUserInRole(role) != true) {
            throw ForbiddenException(message)
        }
    }
}
