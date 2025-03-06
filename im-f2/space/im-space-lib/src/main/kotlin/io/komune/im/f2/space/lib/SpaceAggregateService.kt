package io.komune.im.f2.space.lib

import com.fasterxml.jackson.databind.ObjectMapper
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
import io.komune.im.f2.space.lib.flow.ImMfaPasswordOtpFlow
import io.komune.im.f2.space.lib.flow.SpaceOtpFlowService
import io.komune.im.infra.redis.CacheName
import org.keycloak.representations.idm.RealmRepresentation
import org.keycloak.representations.userprofile.config.UPConfig
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import jakarta.ws.rs.NotFoundException as JakartaNotFoundException

@Service
class SpaceAggregateService(
    private val spaceOtpFlowService: SpaceOtpFlowService,
    private val clientCoreAggregateService: ClientCoreAggregateService,
    private val clientCoreFinderService: ClientCoreFinderService,
    private val authenticationResolver: ImAuthenticationProvider,
    private val objectMapper: ObjectMapper
) : CoreService(CacheName.Space) {

    private val logger = LoggerFactory.getLogger(SpaceAggregateService::class.java)

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

    suspend fun define(
        command: SpaceDefineCommand
    ): SpaceDefinedEvent = withAuth(authenticationResolver.getAuth(), "master") {
        logger.info("Defining space with identifier: ${command.identifier}")
        try {
            update(command)
        } catch (e: JakartaNotFoundException) {
            logger.info("Space not found, creating new space with identifier: ${command.identifier}")
            create(command)
        }
        createFlow(command)
        SpaceDefinedEvent(
            identifier = command.identifier,
        ).also {
            logger.info("Space defined with identifier: ${command.identifier}")
        }
    }

    suspend fun delete(command: SpaceDeleteCommand): SpaceDeletedEvent = mutate(command.id) {
        logger.info("Deleting space with identifier: ${command.id}")
        val client = keycloakClientProvider.get()
        client.realm(command.id).remove()
        logger.info("Space deleted with identifier: ${command.id}")
        SpaceDeletedEvent(command.id)
    }

    private suspend fun create(command: SpaceDefineCommand) {
        logger.info("Creating space with identifier: ${command.identifier}")
        val client = keycloakClientProvider.get()

        val realm = RealmRepresentation()
            .applyBaseConfig()
            .apply(command)

        client.realms().create(realm)
        logger.info("Realm created for space with identifier: ${command.identifier}")

        val authClientId = clientCoreFinderService.getByIdentifier(client.auth.clientId).id
        val realmClientId = clientCoreFinderService.getByIdentifier("${command.identifier}-realm").id
        val realmClientRoles = clientCoreFinderService.listClientRoles(realmClientId)
        ClientGrantClientRolesCommand(
            id = authClientId,
            providerClientId = realmClientId,
            roles = realmClientRoles
        ).let { clientCoreAggregateService.grantClientRoles(it) }
        logger.info("Granted client roles for space with identifier: ${command.identifier}")

        enableUserAttributes(realm.realm)
        logger.info("Enabled user attributes for space with identifier: ${command.identifier}")

        keycloakClientProvider.reset()
    }

    private suspend fun enableUserAttributes(realm: String) {
        logger.info("Enabling user attributes for realm: $realm")
        val client = keycloakClientProvider.get(realm)
        val upConfiguration = client.userProfile().configuration
        upConfiguration.unmanagedAttributePolicy = UPConfig.UnmanagedAttributePolicy.ENABLED
        client.userProfile().update(upConfiguration)
        logger.info("User attributes enabled for realm: $realm")
    }

    private suspend fun update(command: SpaceDefineCommand) {
        logger.info("Updating space with identifier: ${command.identifier}")
        val client = keycloakClientProvider.get()
        val realm = client.realm(command.identifier)
            .toRepresentation()
            .apply(command)
        client.realm(command.identifier).update(realm)
        logger.info("Space updated with identifier: ${command.identifier}")
    }

    private suspend fun createFlow(command: SpaceDefineCommand) {
        if(command.mfa?.contains(SpaceOtpFlowService.OTP_FLOW_USER_ATTRIBUTE_VALUE) == true) {
            val client = keycloakClientProvider.get()
            val acrLoaMap = objectMapper.writeValueAsString(ImMfaPasswordOtpFlow.Acr.asKeycloakMap())
            val settings = client.realm(command.identifier)
                .toRepresentation().apply {
                    attributes["acr.loa.map"] = acrLoaMap
                }
            client.realm(command.identifier).update(settings)

            logger.info("Create custom otp flow[${ImMfaPasswordOtpFlow.name}]: ${command.identifier}")
            spaceOtpFlowService.create(keycloakClientProvider, command.identifier)
            logger.info("Created custom otp flow[${ImMfaPasswordOtpFlow.name}]: ${command.identifier}")
            spaceOtpFlowService.setAsDefault(keycloakClientProvider, command.identifier)
            logger.info("Set as default browser flow[${ImMfaPasswordOtpFlow.name}]: ${command.identifier}")
        } else {
            logger.info("Skip creating custom otp flow: ${command.identifier}")
        }
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
        logger.info("Setting realm to ${command.identifier}")
        realm = command.identifier
        logger.info("Setting displayName to ${command.identifier}")
        displayName = command.identifier
        logger.info("Setting smtpServer to ${command.smtp}")
        smtpServer = command.smtp
        logger.info("Setting loginTheme to ${command.theme}")
        loginTheme = command.theme
        logger.info("Setting emailTheme to ${command.theme}")
        emailTheme = command.theme
        logger.info("Setting supportedLocales to ${command.locales?.toSet()}")
        supportedLocales = command.locales?.toSet()
        logger.info("Setting defaultLocale to ${command.locales?.firstOrNull()}")
        defaultLocale = command.locales?.firstOrNull()
        command.settings?.let { settings ->
            logger.info("Setting isRegistrationAllowed to ${settings.registrationAllowed}")
            isRegistrationAllowed = settings.registrationAllowed
            logger.info("Setting isResetPasswordAllowed to ${settings.resetPasswordAllowed}")
            isResetPasswordAllowed = settings.resetPasswordAllowed
            logger.info("Setting isRegistrationEmailAsUsername to ${settings.registrationEmailAsUsername}")
            isRegistrationEmailAsUsername = settings.registrationEmailAsUsername
            logger.info("Setting isRememberMe to ${settings.rememberMe}")
            isRememberMe = settings.rememberMe
        }
    }
}
