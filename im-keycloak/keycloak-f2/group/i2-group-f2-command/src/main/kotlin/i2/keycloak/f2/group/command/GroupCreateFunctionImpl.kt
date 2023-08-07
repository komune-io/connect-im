package i2.keycloak.f2.group.command

import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.group.domain.features.command.GroupCreateCommand
import i2.keycloak.f2.group.domain.features.command.GroupCreateFunction
import i2.keycloak.f2.group.domain.features.command.GroupCreatedEvent
import i2.keycloak.f2.group.domain.model.GroupId
import i2.keycloak.realm.client.config.AuthRealmClient
import i2.keycloak.realm.client.config.realmsResource
import i2.keycloak.utils.handleResponseError
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

	private fun AuthRealmClient.createSubGroup(parentGroup: GroupId, cmd: GroupCreateCommand): GroupId {
		return toGroupRepresentation(cmd).let { group ->
			realmsResource(cmd.realmId).groups().group(parentGroup).subGroup(group)
		}.handleResponseError("group")
	}


	private fun AuthRealmClient.createGroup(cmd: GroupCreateCommand): GroupId {
		return toGroupRepresentation(cmd)
			.let(
				realmsResource(cmd.realmId).groups()::add
			).handleResponseError("group")
	}

	private fun toGroupRepresentation(cmd: GroupCreateCommand): GroupRepresentation {
		return GroupRepresentation().apply {
			name = cmd.name
			attributes = cmd.attributes.mapValues { (_, value) -> listOfNotNull(value) }
		}
	}

	private fun AuthRealmClient.addRolesToGroup(groupId: GroupId, cmd: GroupCreateCommand) {
		val roles = cmd.roles.map { role ->
			realm.roles()[role].toRepresentation()
		}
		getGroupResource(cmd.realmId, groupId).roles().realmLevel().add(roles)
	}
}
