package io.komune.im.core.user.api

import io.komune.im.api.config.properties.IMProperties
import io.komune.im.commons.auth.AuthenticationProvider
import io.komune.im.commons.utils.mapAsync
import io.komune.im.core.commons.CoreService
import io.komune.im.core.user.domain.command.UserCoreDefineCommand
import io.komune.im.core.user.domain.command.UserCoreDefinedEvent
import io.komune.im.core.user.domain.command.UserCoreDeleteCommand
import io.komune.im.core.user.domain.command.UserCoreDeletedEvent
import io.komune.im.core.user.domain.command.UserCoreDisableCommand
import io.komune.im.core.user.domain.command.UserCoreDisabledEvent
import io.komune.im.core.user.domain.command.UserCoreRemoveCredentialsCommand
import io.komune.im.core.user.domain.command.UserCoreRemovedCredentialsEvent
import io.komune.im.core.user.domain.command.UserCoreSendEmailCommand
import io.komune.im.core.user.domain.command.UserCoreSentEmailEvent
import io.komune.im.core.user.domain.model.UserModel
import io.komune.im.infra.keycloak.handleResponseError
import io.komune.im.infra.redis.CacheName
import java.util.UUID
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserCoreAggregateService(
    private val properties: IMProperties
) : CoreService(CacheName.User) {
    private val logger = LoggerFactory.getLogger(UserCoreAggregateService::class.java)

    suspend fun define(command: UserCoreDefineCommand) = mutate(
        command.id.orEmpty(),
        "Error while defining user (id: [${command.id}], email: [${command.email}])"
    ) {
        val client = keycloakClientProvider.get()

        val existingUser = command.id?.let { client.user(it).toRepresentation() }
        val newRoles = command.roles?.mapAsync {
            client.role(it).toRepresentation()
        }

        val user = (existingUser ?: UserRepresentation()).apply(command)

        val userId = if (existingUser == null) {
            user.isEnabled = true
            val userId = client.users().create(user).handleResponseError("User")
            user.id = userId
            userId
        } else {
            client.user(existingUser.id).update(user)
            existingUser.id
        }

        if (command.canUpdateMemberOf()) {
            command.memberOf?.let {
                client.user(userId).joinGroup(it)
            }
        }

        if (command.password != null) {
            val credentials = CredentialRepresentation().also {
                it.type = CredentialRepresentation.PASSWORD
                it.value = command.password
                it.isTemporary = command.isPasswordTemporary
            }
            client.user(userId).resetPassword(credentials)
        }

        newRoles?.let { user.assignRoles(it) }

        UserCoreDefinedEvent(userId)
    }

    suspend fun sendEmail(command: UserCoreSendEmailCommand) = handleErrors(
        "Error sending email actions [${command.actions.joinToString(", ")}] " +
            "userId[${command.id}] " +
            "clientId[${AuthenticationProvider.getClientId()}]"
    ) {
        val client = keycloakClientProvider.get()

        val clientId = AuthenticationProvider.getClientId()
        val redirectUri = clientId?.let {
            client.getClientByIdentifier(clientId)?.redirectUris?.firstOrNull()
        }
        client.user(command.id).executeActionsEmail(clientId, redirectUri, command.actions.toList())

        UserCoreSentEmailEvent(command.id, command.actions)
    }

    suspend fun disable(command: UserCoreDisableCommand) = mutate(
        command.id,
        "Error disabling user [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        val user = client.user(command.id).toRepresentation()

        user.isEnabled = false
        user.singleAttribute<UserRepresentation>(UserModel::disabledBy.name, command.disabledBy)
        user.singleAttribute<UserRepresentation>(UserModel::disabledDate.name, System.currentTimeMillis().toString())

        client.user(command.id).update(user)
        UserCoreDisabledEvent(command.id)
    }

    suspend fun removeCredentials(command: UserCoreRemoveCredentialsCommand) = mutate(
        command.id,
        "User[${command.id}] Error removing credentials with type ${command.type}."
    ) {
        val useClient = keycloakClientProvider.get().user(command.id)
        val credentials = useClient.credentials()
        credentials.forEach {
            if(it.type == command.type.value) {
                useClient.removeCredential(it.id)
            }
        }
        UserCoreRemovedCredentialsEvent(command.id)
    }

    suspend fun delete(command: UserCoreDeleteCommand) = mutate(command.id, "Error deleting user [${command.id}]") {
        val client = keycloakClientProvider.get()
        client.user(command.id).remove()
        UserCoreDeletedEvent(command.id)
    }

    private suspend fun UserRepresentation.apply(command: UserCoreDefineCommand): UserRepresentation {
        val emailAsUsername = getIsRegistrationEmailAsUsername()
        return this.apply {
            username =
                username ?: if (emailAsUsername) email ?: UUID.randomUUID().toString() else UUID.randomUUID().toString()
            command.email?.let { email = it }
            command.givenName?.let { firstName = it }
            command.familyName?.let { lastName = it }
            command.isEmailVerified?.let { isEmailVerified = it }

            val baseAttributes = mapOf(
                UserModel::creationDate.name to System.currentTimeMillis().toString(),
            ).mapValues { (_, values) -> listOf(values) }

            val newAttributes = command.attributes.orEmpty().plus(
                listOfNotNull(
                    command.memberOf
                        .takeIf { command.canUpdateMemberOf() }
                        ?.let { UserModel::memberOf.name to command.memberOf },
                    UserModel::isApiKey.name to command.isApiKey.toString(),
                ).toMap()
            ).mapValues { (_, values) -> listOf(values) }

            attributes = baseAttributes
                .plus(attributes.orEmpty())
                .plus(newAttributes)
                .filterValues { it.filterNotNull().isNotEmpty() }
        }
    }

    private suspend fun getIsRegistrationEmailAsUsername(): Boolean {
        val emailAsUsername = properties.user?.emailAsUsername
            ?: keycloakClientProvider.get().realm().toRepresentation()?.let {
                logger.info("Using realm[${it.displayName}] configuration " +
                    "for emailAsUsername: ${it.isRegistrationEmailAsUsername}")
                it.isRegistrationEmailAsUsername
            } ?: false
        return emailAsUsername
    }

    private suspend fun UserRepresentation.assignRoles(roles: List<RoleRepresentation>) {
        val client = keycloakClientProvider.get()
        with(client.user(id).roles().realmLevel()) {
            val allRoles = listAll()
            logger.info("All roles: ${allRoles.map { it.name }}")
            val rolesToRemoves = allRoles.filterNot { it.name.startsWith("default-roles") }
            logger.info("User[$id] Removing roles: ${rolesToRemoves.map { it.name }}")
            remove(rolesToRemoves)
            logger.info("Adding roles: ${roles.map { it.name }}")
            add(roles)
        }
    }

    private fun UserCoreDefineCommand.canUpdateMemberOf(): Boolean {
        return id == null || isApiKey || memberOf != null
    }
}
