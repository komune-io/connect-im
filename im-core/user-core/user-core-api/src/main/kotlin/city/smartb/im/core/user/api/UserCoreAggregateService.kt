package city.smartb.im.core.user.api

import city.smartb.im.commons.auth.AuthenticationProvider
import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.user.domain.command.UserCoreDefineCommand
import city.smartb.im.core.user.domain.command.UserCoreDefinedEvent
import city.smartb.im.core.user.domain.command.UserCoreDeleteCommand
import city.smartb.im.core.user.domain.command.UserCoreDeletedEvent
import city.smartb.im.core.user.domain.command.UserCoreDisableCommand
import city.smartb.im.core.user.domain.command.UserCoreDisabledEvent
import city.smartb.im.core.user.domain.command.UserCoreSendEmailCommand
import city.smartb.im.core.user.domain.command.UserCoreSentEmailEvent
import city.smartb.im.core.user.domain.model.UserModel
import city.smartb.im.infra.keycloak.handleResponseError
import city.smartb.im.infra.redis.CacheName
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.RoleRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserCoreAggregateService: CoreService(CacheName.User) {

    suspend fun define(command: UserCoreDefineCommand) = mutate(command.id.orEmpty(),
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

        UserCoreSentEmailEvent(command.id, command.actions)
    }

    suspend fun disable(command: UserCoreDisableCommand) = mutate(command.id,
        "Error disabling user [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        val user = client.user(command.id).toRepresentation()

        user.isEnabled = false
        user.singleAttribute(UserModel::disabledBy.name, command.disabledBy)
        user.singleAttribute(UserModel::disabledDate.name, System.currentTimeMillis().toString())

        client.user(command.id).update(user)
        UserCoreDisabledEvent(command.id)
    }

    suspend fun delete(command: UserCoreDeleteCommand) = mutate(command.id, "Error deleting user [${command.id}]") {
        val client = keycloakClientProvider.get()
        client.user(command.id).remove()
        UserCoreDeletedEvent(command.id)
    }

    private fun UserRepresentation.apply(command: UserCoreDefineCommand) = apply {
        username = username ?: UUID.randomUUID().toString()
        command.email?.let { email = it }
        command.givenName?.let { firstName = it }
        command.familyName?.let { lastName = it }
        command.isEmailVerified?.let { isEmailVerified = it }
        isEnabled = true

        val baseAttributes = mapOf(
            UserModel::creationDate.name to System.currentTimeMillis().toString(),
        ).mapValues { (_, values) -> listOf(values) }

        val newAttributes = command.attributes.orEmpty().plus(
            listOfNotNull(
                command.memberOf.takeIf { command.canUpdateMemberOf() } ?.let { UserModel::memberOf.name to command.memberOf },
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

    private fun UserCoreDefineCommand.canUpdateMemberOf(): Boolean {
        return id == null || isApiKey
    }
}
