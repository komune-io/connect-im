package io.komune.im.f2.space.lib

import io.komune.im.api.config.bean.ImAuthenticationProvider
import io.komune.im.commons.auth.withAuth
import io.komune.im.core.client.api.ClientCoreAggregateService
import io.komune.im.core.client.api.ClientCoreFinderService
import io.komune.im.core.client.domain.command.ClientGrantClientRolesCommand
import io.komune.im.core.commons.CoreService
import io.komune.im.f2.space.domain.command.SpaceDefineCommand
import io.komune.im.f2.space.domain.command.SpaceDefinedEvent
import io.komune.im.f2.space.domain.command.SpaceDeleteCommand
import io.komune.im.f2.space.domain.command.SpaceDeletedEvent
import io.komune.im.infra.keycloak.client.KeycloakClient
import io.komune.im.infra.redis.CacheName
import org.keycloak.representations.idm.RealmRepresentation
import org.keycloak.representations.userprofile.config.UPConfig
import org.springframework.stereotype.Service
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

@Service
class SpaceAggregateService(
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
    private val authenticationResolver: ImAuthenticationProvider
): CoreService(CacheName.Space) {


    companion object {
        const val ACCESS_TOKEN_LIFESPAN = 28800
        const val SSO_SESSSION_IDLE_TIMEOUT = 604800
        const val SSO_SESSION_MAX_LIFESPAN = 259200
        const val ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN = 900
        const val IS_VERIFY_EMAIL = true
        const val ID_RESET_PASSWORD_ALLOWED = true
        const val PASSWORD_POLICY = "length(8)"
        const val BASE_THEME = "keycloak"
        const val IS_INTERNATIONALIZATION_ENABLED = true
    }

    suspend fun define(command: SpaceDefineCommand): SpaceDefinedEvent = withAuth(authenticationResolver.getAuth(), "master") {
        try {
            update(command)
        } catch (e: JakartaNotFoundException) {
            create(command)
        }

        SpaceDefinedEvent(
            identifier = command.identifier,
        )
    }

    suspend fun delete(command: SpaceDeleteCommand): SpaceDeletedEvent = mutate(command.id) {
        val client = keycloakClientProvider.get()
        client.realm(command.id).remove()
        SpaceDeletedEvent(command.id)
    }

    private suspend fun create(command: SpaceDefineCommand) {
        val client = keycloakClientProvider.get()

        val realm = RealmRepresentation()
            .applyBaseConfig()
            .apply(command)

        client.realms().create(realm)

        val authClientId = clientCoreFinderService.getByIdentifier(client.auth.clientId).id
        val realmClientId = clientCoreFinderService.getByIdentifier("${command.identifier}-realm").id
        val realmClientRoles = clientCoreFinderService.listClientRoles(realmClientId)
        ClientGrantClientRolesCommand(
            id = authClientId,
            providerClientId = realmClientId,
            roles = realmClientRoles
        ).let { clientCoreAggregateService.grantClientRoles(it) }

        enableUserAttributes(realm.realm)

        keycloakClientProvider.reset()
    }

    private suspend fun enableUserAttributes(realm: String) {
        val client = keycloakClientProvider.get(realm)
        val upConfiguration = client.userProfile().configuration
        upConfiguration.unmanagedAttributePolicy = UPConfig.UnmanagedAttributePolicy.ENABLED
        client.userProfile().update(upConfiguration)
    }

    private suspend fun update(command: SpaceDefineCommand) {
        val client = keycloakClientProvider.get()
        val realm = client.realm(command.identifier)
            .toRepresentation()
            .apply(command)
        client.realm(command.identifier).update(realm)
    }

    private fun RealmRepresentation.applyBaseConfig() = apply {
        isEnabled = true
        isInternationalizationEnabled = IS_INTERNATIONALIZATION_ENABLED

        accessTokenLifespan = ACCESS_TOKEN_LIFESPAN
        ssoSessionIdleTimeout = SSO_SESSSION_IDLE_TIMEOUT
        ssoSessionMaxLifespan = SSO_SESSION_MAX_LIFESPAN
        actionTokenGeneratedByUserLifespan = ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN

        isVerifyEmail = IS_VERIFY_EMAIL
        isResetPasswordAllowed = ID_RESET_PASSWORD_ALLOWED
        passwordPolicy = PASSWORD_POLICY

        loginTheme = BASE_THEME
        accountTheme = BASE_THEME
        adminTheme = BASE_THEME
        emailTheme = BASE_THEME
    }

    private fun RealmRepresentation.apply(command: SpaceDefineCommand) = apply {
        realm = command.identifier
        displayName = command.identifier
        smtpServer = command.smtp
        loginTheme = command.theme
        emailTheme = command.theme
        supportedLocales = command.locales?.toSet()
        defaultLocale = command.locales?.firstOrNull()
    }
}
