package i2.keycloak.f2.group.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import i2.keycloak.f2.commons.app.keycloakF2Function
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import i2.keycloak.f2.group.domain.features.command.GroupUpdateFunction
import i2.keycloak.f2.group.domain.features.command.GroupUpdatedEvent
import i2.keycloak.f2.group.domain.model.HiddenGroupAttributes
import org.keycloak.admin.client.resource.GroupResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GroupUpdateFunctionImpl {
	@Bean
	fun groupUpdateFunction(): GroupUpdateFunction = keycloakF2Function { cmd, client ->
		try {
			val groupResource = client.group(cmd.id)

			groupResource.removeAllRoles()
			groupResource.setRoles(client, cmd.roles)

			groupResource.toRepresentation().apply {
				name = cmd.name
                val mergedAttrs = attributes.filter { it.key in HiddenGroupAttributes }
                val cmdAttrs = cmd.attributes.mapValues { (_, value) -> listOfNotNull(value) }
				attributes = mergedAttrs + cmdAttrs

			}.let(groupResource::update)

			GroupUpdatedEvent(cmd.id)
		} catch (e: Exception) {
			throw I2ApiError(
				description = "Realm[${cmd.realmId}] Group[${cmd.id}] Error updating",
				payload = emptyMap()
			).asI2Exception(e)
		}
		GroupUpdatedEvent(cmd.id)
	}

	private fun GroupResource.removeAllRoles() {
		this.roles().realmLevel().remove(this.roles().realmLevel().listAll())
	}

	private fun GroupResource.setRoles(client: KeycloakClient, roles: List<String>) {
		val roleRepresentations = roles.map { role ->
			client.role(role).toRepresentation()
		}
		roles().realmLevel().add(roleRepresentations)
	}
}
