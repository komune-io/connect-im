package city.smartb.im.organization.api.service

import city.smartb.im.commons.ImMessage
import city.smartb.im.commons.utils.toJson
import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreateResult
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.command.OrganizationUpdateResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.model.Organization
import f2.dsl.fnc.invoke
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import javax.ws.rs.NotFoundException
import org.springframework.stereotype.Service

@Service
class OrganizationAggregateService(
    private val groupCreateFunction: GroupCreateFunction,
    private val groupUpdateFunction: GroupUpdateFunction,
    private val organizationFinderService: OrganizationFinderService
) {

    suspend fun organizationCreate(command: ImMessage<OrganizationCreateCommand>): OrganizationCreateResult {
        return groupCreateFunction.invoke(command.toGroupCreateCommand())
            .id
            .let{ groupId ->
                OrganizationCreateResult(
                    parentOrganization = command.payload.parentOrganizationId,
                    id = groupId
                )
            }
    }

    suspend fun organizationUpdate(command: ImMessage<OrganizationUpdateCommand>): OrganizationUpdateResult {
        val organization = organizationFinderService.organizationGet(ImMessage(
                authRealm = command.authRealm,
                realmId = command.realmId,
                payload = OrganizationGetQuery(id = command.payload.id))
            ).item
            ?: throw NotFoundException("Organization [${command.payload.id}] not found")

        return groupUpdateFunction.invoke(command.toGroupUpdateCommand(organization))
            .let { result -> OrganizationUpdateResult(result.id) }
    }

    private fun ImMessage<OrganizationCreateCommand>.toGroupCreateCommand() = GroupCreateCommand(
        name = payload.name,
        attributes = mapOf(
            payload::siret.name to payload.siret,
            payload::address.name to payload.address.toJson(),
            payload::description.name to payload.description,
            payload::website.name to payload.website
        ).mapValues { (_, value) -> listOfNotNull(value) },
        roles = payload.roles ?: emptyList(),
        realmId = realmId,
        auth = authRealm,
        parentGroupId = payload.parentOrganizationId
    )

    private fun ImMessage<OrganizationUpdateCommand>.toGroupUpdateCommand(organization: Organization) = GroupUpdateCommand(
        id = payload.id,
        name = payload.name,
        attributes = mapOf(
            organization::siret.name to organization.siret,
            payload::address.name to payload.address.toJson(),
            payload::description.name to payload.description,
            payload::website.name to payload.website
        ).mapValues { (_, value) -> listOfNotNull(value) },
        roles = payload.roles ?: emptyList(),
        realmId = realmId,
        auth = authRealm
    )
}
