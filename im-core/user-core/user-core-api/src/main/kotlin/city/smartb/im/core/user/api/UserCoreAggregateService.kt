package city.smartb.im.core.user.api

import city.smartb.im.commons.auth.AuthenticationProvider
import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.user.domain.command.UserDefineCommand
import city.smartb.im.core.user.domain.command.UserDefinedEvent
import city.smartb.im.core.user.domain.command.UserDeleteCommand
import city.smartb.im.core.user.domain.command.UserDeletedEvent
import city.smartb.im.core.user.domain.command.UserDisableCommand
import city.smartb.im.core.user.domain.command.UserDisabledEvent
import city.smartb.im.core.user.domain.command.UserSendEmailCommand
import city.smartb.im.core.user.domain.command.UserSentEmailEvent
import city.smartb.im.core.user.domain.model.User
import city.smartb.im.infra.keycloak.handleResponseError
import city.smartb.im.infra.redis.CacheName
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserCoreAggregateService: CoreService(CacheName.User) {

    suspend fun define(command: UserDefineCommand) = mutate(command.id.orEmpty(),
        "Error while defining user (id: [${command.id}], email: [${command.email}])"
    ) {
        val client = keycloakClientProvider.get()

        val existingUser = command.id?.let { client.user(it).toRepresentation() }
        val newRoles = command.roles?.mapAsync {
            client.role(it).toRepresentation()
        }

        val user = (existingUser ?: UserRepresentation()).apply(command)

        val userId = if (existingUser == null) {
            val userId = client.users().create(user).handleResponseError("User")
            user.id = userId
            userId
        } else {
            client.user(existingUser.id).update(user)
            existingUser.id
        }

        // enable memberOf update to allow apikeys service accounts to join their organisation
        command.memberOf?.let {
            if (it !== user.attributes[User::memberOf.name]?.firstOrNull()) {
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

        UserDefinedEvent(userId)
    }

    suspend fun sendEmail(command: UserSendEmailCommand) = handleErrors(
        "Error sending email actions [${command.actions.joinToString(", ")}]" +
            "userId[${command.id}] " +
            "clientId[${AuthenticationProvider.getClientId()}]"
    ) {
        val client = keycloakClientProvider.get()

        val clientId = AuthenticationProvider.getClientId()
        val redirectUri = clientId?.let {
            client.getClientByIdentifier(clientId)?.redirectUris?.firstOrNull()
        }
        client.user(command.id).executeActionsEmail(clientId, redirectUri, command.actions.toList())

        UserSentEmailEvent(command.id, command.actions)
    }

    suspend fun disable(command: UserDisableCommand) = mutate(command.id,
        "Error disabling user [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        val user = client.user(command.id).toRepresentation()

        user.isEnabled = false
        user.singleAttribute(User::disabledBy.name, command.disabledBy)
        user.singleAttribute(User::disabledDate.name, System.currentTimeMillis().toString())

        client.user(command.id).update(user)
        UserDisabledEvent(command.id)
    }

    suspend fun delete(command: UserDeleteCommand) = mutate(command.id, "Error deleting user [${command.id}]") {
        val client = keycloakClientProvider.get()
        client.user(command.id).remove()
        UserDeletedEvent(command.id)
    }

    private fun UserRepresentation.apply(command: UserDefineCommand) = apply {
        username = username ?: UUID.randomUUID().toString()
        command.email?.let { email = it }
        command.givenName?.let { firstName = it }
        command.familyName?.let { lastName = it }
        command.isEmailVerified?.let { isEmailVerified = it }
        isEnabled = true

        val baseAttributes = mapOf(
            User::creationDate.name to System.currentTimeMillis().toString(),
        ).mapValues { (_, values) -> listOf(values) }

        val newAttributes = command.attributes.orEmpty().plus(
            listOfNotNull(
                command.memberOf.takeIf { command.id == null }?.let { User::memberOf.name to command.memberOf },
            ).toMap()
        ).mapValues { (_, values) -> listOf(values) }

        attributes = baseAttributes
            .plus(attributes.orEmpty())
            .plus(newAttributes)
            .filterValues { it.filterNotNull().isNotEmpty() }
    }

    private suspend fun UserRepresentation.assignRoles(roles: List<RoleRepresentation>) {
        val client = keycloakClientProvider.get()
        with(client.user(id).roles().realmLevel()) {
            remove(listAll())
            add(roles)
        }
    }
}
