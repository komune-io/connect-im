package i2.keycloak.plugin.action.token

import org.keycloak.authentication.actiontoken.execactions.ExecuteActionsActionToken
import org.keycloak.models.KeycloakSession
import org.keycloak.services.managers.AppAuthManager
import javax.ws.rs.ForbiddenException
import javax.ws.rs.GET
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

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
