package io.komune.im.keycloak.plugin.welcome

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.ForbiddenException
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.concurrent.TimeUnit
import org.keycloak.authentication.actiontoken.execactions.ExecuteActionsActionToken
import org.keycloak.common.util.Time
import org.keycloak.email.EmailException
import org.keycloak.email.EmailTemplateProvider
import org.keycloak.events.admin.OperationType
import org.keycloak.events.admin.ResourceType
import org.keycloak.models.ClientModel
import org.keycloak.models.Constants
import org.keycloak.models.KeycloakSession
import org.keycloak.models.RealmModel
import org.keycloak.models.UserModel
import org.keycloak.models.utils.SystemClientUtil
import org.keycloak.protocol.oidc.utils.RedirectUtils
import org.keycloak.services.ErrorResponse
import org.keycloak.services.ServicesLogger
import org.keycloak.services.resources.LoginActionsService
import org.keycloak.services.resources.admin.AdminEventBuilder
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator
import org.keycloak.theme.Theme
import org.keycloak.theme.beans.LinkExpirationFormatterMethod
import org.keycloak.authentication.requiredactions.util.RequiredActionsValidator

/**
 * `UserResource.executeActionsEmail` with its own subject and template: Keycloak's
 * `sendExecuteActions` hardcodes both. The checks follow `UserResource.verifySendEmailParams`
 * and must be re-read against it at every Keycloak upgrade.
 */
class WelcomeEmailAdminResource(
    private val session: KeycloakSession,
    private val realm: RealmModel,
    private val auth: AdminPermissionEvaluator,
    private val adminEvent: AdminEventBuilder
) {

    @Suppress("SwallowedException")
    @PUT
    @Path("users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun send(
        @PathParam("id") id: String,
        @QueryParam("client_id") clientId: String?,
        @QueryParam("redirect_uri") redirectUri: String?,
        @QueryParam("lifespan") lifespan: Int?,
        actions: List<String>?
    ): Response {
        val user = findManagedUser(id)
        val target = verifiedTarget(user, clientId, redirectUri)
        if (!actions.isNullOrEmpty() && !RequiredActionsValidator.validRequiredActions(session, actions)) {
            throw ErrorResponse.error("Provided invalid required actions", Response.Status.BAD_REQUEST)
        }

        val theme = session.theme().getTheme(Theme.Type.EMAIL)
        // A realm whose theme has no welcome template keeps the mail, and the lifespan, it always had.
        val hasWelcome = theme.getTemplate("html/$WELCOME_TEMPLATE") != null
        val seconds = lifespan.takeIf { hasWelcome } ?: realm.actionTokenGeneratedByAdminLifespan
        val token = ExecuteActionsActionToken(
            user.id, user.email, Time.currentTime() + seconds, actions, target.redirectUri, target.clientId
        )
        val link = LoginActionsService.actionTokenProcessor(session.context.uri)
            .queryParam("key", token.serialize(session, realm, session.context.uri))
            .build(realm.name)
            .toString()
        val minutes = TimeUnit.SECONDS.toMinutes(seconds.toLong())
        val mail = session.getProvider(EmailTemplateProvider::class.java).setRealm(realm).setUser(user)

        try {
            if (hasWelcome) {
                val locale = session.context.resolveLocale(user, theme.type)
                mail.send(
                    WELCOME_SUBJECT,
                    WELCOME_TEMPLATE,
                    hashMapOf(
                        Constants.TEMPLATE_ATTR_REQUIRED_ACTIONS to token.requiredActions,
                        "link" to link,
                        "linkExpiration" to minutes,
                        "linkExpirationFormatter" to LinkExpirationFormatterMethod(theme.getMessages(locale), locale),
                    )
                )
            } else {
                mail.setAttribute(Constants.TEMPLATE_ATTR_REQUIRED_ACTIONS, token.requiredActions)
                    .sendExecuteActions(link, minutes)
            }
        } catch (e: EmailException) {
            ServicesLogger.LOGGER.failedToSendActionsEmail(e)
            throw ErrorResponse.error(
                "Failed to send welcome email: ${e.message}",
                Response.Status.INTERNAL_SERVER_ERROR
            )
        }

        adminEvent.resource(ResourceType.USER).operation(OperationType.ACTION)
            .resourcePath(session.context.uri).success()
        return Response.noContent().build()
    }

    private fun findManagedUser(id: String): UserModel {
        val user = session.users().getUserById(realm, id)
        if (user == null) {
            throw if (auth.users().canQuery()) NotFoundException("User not found") else ForbiddenException()
        }
        auth.users().requireManage(user)
        return user
    }

    @Suppress("ThrowsCount")
    private fun verifiedTarget(user: UserModel, clientId: String?, redirectUri: String?): Target {
        if (user.email == null) throw badRequest("User email missing")
        if (!user.isEnabled) throw badRequest("User is disabled")
        if (redirectUri != null && clientId == null) throw badRequest("Client id missing")

        val client: ClientModel = (if (clientId != null) realm.getClientByClientId(clientId)
            else SystemClientUtil.getSystemClient(realm))
            ?: throw badRequest("Client doesn't exist")
        if (!client.isEnabled) throw badRequest("Client is not enabled")

        val verified = redirectUri?.let {
            RedirectUtils.verifyRedirectUri(session, it, client) ?: throw badRequest("Invalid redirect uri.")
        }
        return Target(client.clientId, verified)
    }

    private fun badRequest(message: String) = ErrorResponse.error(message, Response.Status.BAD_REQUEST)

    private data class Target(val clientId: String, val redirectUri: String?)

    private companion object {
        const val WELCOME_SUBJECT = "welcomeSubject"
        const val WELCOME_TEMPLATE = "welcome.ftl"
    }
}
