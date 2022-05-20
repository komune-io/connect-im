package city.smartb.im.organization.api.service

import city.smartb.im.api.config.ImKeycloakConfig
import city.smartb.im.organization.domain.features.command.OrganizationCreateCommand
import city.smartb.im.organization.domain.features.command.OrganizationCreateResult
import city.smartb.im.organization.domain.features.command.OrganizationUpdateCommand
import city.smartb.im.organization.domain.features.command.OrganizationUpdateResult
import city.smartb.im.organization.domain.features.query.OrganizationGetQuery
import city.smartb.im.organization.domain.model.Organization
import f2.dsl.fnc.invoke
import i2.commons.utils.toJson
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdateCommand
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import javax.ws.rs.NotFoundException
import org.springframework.stereotype.Service

@Service
class OrganizationAggregateService(
    private val imKeycloakConfig: ImKeycloakConfig,
    private val groupCreateFunction: GroupCreateFunction,
    private val groupUpdateFunction: GroupUpdateFunction,
    private val organizationFinderService: OrganizationFinderService
) {

    suspend fun organizationCreate(command: OrganizationCreateCommand): OrganizationCreateResult {
        return groupCreateFunction.invoke(command.toGroupCreateCommand())
            .id
            .let{ groupId ->
                OrganizationCreateResult(
                    parentOrganization = command.parentOrganizationId,
                    id = groupId
                )
            }
    }

    suspend fun organizationUpdate(command: OrganizationUpdateCommand): OrganizationUpdateResult {
        val organization = organizationFinderService.organizationGet(
            OrganizationGetQuery(id = command.id))
            .item
            ?: throw NotFoundException("Organization [${command.id}] not found")

        return groupUpdateFunction.invoke(command.toGroupUpdateCommand(organization))
            .let { result -> OrganizationUpdateResult(result.id) }
    }

    private fun OrganizationCreateCommand.toGroupCreateCommand() = GroupCreateCommand(
        name = name,
        attributes = mapOf(
            ::siret.name to siret,
            ::address.name to address.toJson(),
            ::description.name to description,
            ::website.name to website
        ).mapValues { (_, value) -> listOfNotNull(value) },
        roles = roles ?: emptyList(),
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm(),
        parentGroupId = parentOrganizationId
    )

    private fun OrganizationUpdateCommand.toGroupUpdateCommand(organization: Organization) = GroupUpdateCommand(
        id = id,
        name = name,
        attributes = mapOf(
            organization::siret.name to organization.siret,
            ::address.name to address.toJson(),
            ::description.name to description,
            ::website.name to website
        ).mapValues { (_, value) -> listOfNotNull(value) },
        roles = roles ?: emptyList(),
        realmId = imKeycloakConfig.realm,
        auth = imKeycloakConfig.authRealm()
    )
}
