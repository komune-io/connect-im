package city.smartb.im.core.organization.api

import city.smartb.im.commons.utils.mapAsync
import city.smartb.im.commons.utils.toJson
import city.smartb.im.core.commons.CoreService
import city.smartb.im.core.organization.domain.command.OrganizationCoreDefineCommand
import city.smartb.im.core.organization.domain.command.OrganizationCoreDefinedEvent
import city.smartb.im.core.organization.domain.command.OrganizationCoreDeleteCommand
import city.smartb.im.core.organization.domain.command.OrganizationCoreDeletedEvent
import city.smartb.im.core.organization.domain.command.OrganizationCoreSetSomeAttributesCommand
import city.smartb.im.core.organization.domain.command.OrganizationCoreSetSomeAttributesEvent
import city.smartb.im.core.organization.domain.model.OrganizationModel
import city.smartb.im.infra.keycloak.handleResponseError
import city.smartb.im.infra.redis.CacheName
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.stereotype.Service

@Service
class OrganizationCoreAggregateService: CoreService(CacheName.Organization) {

    suspend fun define(command: OrganizationCoreDefineCommand) = mutate(command.id.orEmpty(),
        "Error while defining organization (id: [${command.id}], identifier: [${command.identifier}])"
    ) {
        val client = keycloakClientProvider.get()

        val existingGroup = command.id?.let { client.group(it).toRepresentation() }
        val newRoles = command.roles.orEmpty().mapAsync {
            client.role(it).toRepresentation()
        }

        val group = (existingGroup ?: GroupRepresentation()).apply {
            name = command.identifier

            val baseAttributes = mapOf(
                OrganizationModel::creationDate.name to System.currentTimeMillis().toString(),
                OrganizationModel::enabled.name to "true",
            ).mapValues { (_, values) -> listOf(values) }

            val newAttributes = command.attributes.orEmpty().plus(mapOf(
                OrganizationModel::displayName.name to command.displayName,
                OrganizationModel::description.name to command.description,
                OrganizationModel::address.name to command.address.toJson(),
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

        OrganizationCoreDefinedEvent(groupId)
    }

    suspend fun setSomeAttributes(command: OrganizationCoreSetSomeAttributesCommand) = mutate(command.id,
        "Error while setting some attributes of organization [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        val group = client.group(command.id).toRepresentation()
        command.attributes.forEach { (key, value) ->
            group.singleAttribute(key, value)
        }
        client.group(command.id).update(group)

        OrganizationCoreSetSomeAttributesEvent(
            id = command.id,
            attributes = command.attributes
        )
    }

    suspend fun delete(command: OrganizationCoreDeleteCommand): OrganizationCoreDeletedEvent = mutate(command.id,
        "Error while deleting organization [${command.id}]"
    ) {
        val client = keycloakClientProvider.get()
        client.group(command.id).remove()
        OrganizationCoreDeletedEvent(command.id)
    }
}
