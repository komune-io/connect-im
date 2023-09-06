package city.smartb.im.core.organization.api

import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.organization.domain.command.OrganizationDefineCommand
import city.smartb.im.core.organization.domain.command.OrganizationDefinedEvent
import city.smartb.im.core.organization.domain.command.OrganizationDeleteCommand
import city.smartb.im.core.organization.domain.command.OrganizationDeletedEvent
import city.smartb.im.core.organization.domain.command.OrganizationSetSomeAttributesCommand
import city.smartb.im.core.organization.domain.command.OrganizationSetSomeAttributesEvent
import city.smartb.im.core.organization.domain.model.Organization
import city.smartb.im.infra.keycloak.handleResponseError
import city.smartb.im.infra.redis.CacheName
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.stereotype.Service

@Service
class OrganizationCoreAggregateService: CoreService(CacheName.Organization) {

    suspend fun define(command: OrganizationDefineCommand) = mutate(command.id.orEmpty(),
        "Error while defining organization (id: [${command.id}], identifier: [${command.identifier}])"
    ) {
        val client = keycloakClientProvider.get()

        val existingGroup = command.id?.let { client.group(it).toRepresentation() }
        val newRoles = command.roles.orEmpty().map {
            async { client.role(it).toRepresentation() }
        }.awaitAll()

        val group = (existingGroup ?: GroupRepresentation()).apply {
            name = command.identifier

            val baseAttributes = mapOf(
                Organization::creationDate.name to System.currentTimeMillis().toString(),
                Organization::enabled.name to "true",
            ).mapValues { (_, values) -> listOf(values) }

            val newAttributes = command.attributes.orEmpty().plus(mapOf(
                Organization::displayName.name to command.displayName,
                Organization::description.name to command.description,
                Organization::address.name to command.address.toJson(),
            )).mapValues { (_, values) -> listOf(values) }

            attributes = baseAttributes
                .plus(attributes.orEmpty())
                .plus(newAttributes)
                .filterValues { it.filterNotNull().isNotEmpty() }
        }

        val groupId = if (command.parentOrganizationId != null) {
            client.group(command.parentOrganizationId!!)
                .subGroup(group)
                .handleResponseError("Organization")
        } else if (existingGroup == null) {
            client.groups()
                .add(group)
                .handleResponseError("Organization")
        } else {
            client.group(existingGroup.id).update(group)
            existingGroup.id
        }

        if (existingGroup != null) {
            val oldRoles = client.group(groupId).roles().realmLevel().listAll()
            client.group(groupId).roles().realmLevel().remove(oldRoles)
        }
        client.group(groupId).roles().realmLevel().add(newRoles)

        OrganizationDefinedEvent(groupId)
    }

    suspend fun setSomeAttributes(command: OrganizationSetSomeAttributesCommand) = mutate(command.id,
        "Error while setting some attributes of organization [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        val group = client.group(command.id).toRepresentation()
        command.attributes.forEach { (key, value) ->
            group.singleAttribute(key, value)
        }
        client.group(command.id).update(group)

        OrganizationSetSomeAttributesEvent(
            id = command.id,
            attributes = command.attributes
        )
    }

    suspend fun delete(command: OrganizationDeleteCommand): OrganizationDeletedEvent = mutate(command.id,
        "Error while deleting organization [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        client.group(command.id).remove()
        OrganizationDeletedEvent(command.id)
    }
}
