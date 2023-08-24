package i2.keycloak.f2.group.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.handleResponseError
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupCreatedEvent
import i2.keycloak.f2.group.domain.model.GroupId
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupCreateFunctionImpl {

	@Bean
	fun groupCreateFunction(): GroupCreateFunction = keycloakF2Function { cmd, client ->
		val groupId = cmd.parentGroupId?.let { parentGroup ->
			client.createSubGroup(parentGroup, cmd)
		} ?: client.createGroup(cmd)

		client.addRolesToGroup(groupId, cmd)

		GroupCreatedEvent(groupId)
	}

	private fun KeycloakClient.createSubGroup(parentGroup: GroupId, cmd: GroupCreateCommand): GroupId {
		return toGroupRepresentation(cmd).let { group ->
			group(parentGroup).subGroup(group)
		}.handleResponseError("group")
	}


	private fun KeycloakClient.createGroup(cmd: GroupCreateCommand): GroupId {
		return toGroupRepresentation(cmd)
			.let(groups()::add)
            .handleResponseError("group")
	}

	private fun toGroupRepresentation(cmd: GroupCreateCommand): GroupRepresentation {
		return GroupRepresentation().apply {
			name = cmd.name
			attributes = cmd.attributes.mapValues { (_, value) -> listOfNotNull(value) }
		}
	}

	private fun KeycloakClient.addRolesToGroup(groupId: GroupId, cmd: GroupCreateCommand) {
		val roles = cmd.roles.map { role ->
			role(role).toRepresentation()
		}
		group(groupId).roles().realmLevel().add(roles)
	}
}
