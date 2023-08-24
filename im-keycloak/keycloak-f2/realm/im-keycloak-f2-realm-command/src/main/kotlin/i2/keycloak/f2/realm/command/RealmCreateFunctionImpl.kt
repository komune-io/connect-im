package i2.keycloak.f2.realm.command

import city.smartb.im.infra.keycloak.client.KeycloakClient
import city.smartb.im.infra.keycloak.client.KeycloakClientBuilder
import city.smartb.im.infra.keycloak.client.buildRealmRepresentation
import f2.dsl.fnc.f2Function
import i2.keycloak.f2.realm.domain.features.command.RealmCreateCommand
import i2.keycloak.f2.realm.domain.features.command.RealmCreateFunction
import i2.keycloak.f2.realm.domain.features.command.RealmCreatedEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RealmCreateFunctionImpl {

	@Bean
	fun realmCreateFunction(): RealmCreateFunction = f2Function { cmd ->
        val client = KeycloakClientBuilder.openConnection(cmd.masterRealmAuth).forAuthedRealm()
        client.createRealm(cmd)
		RealmCreatedEvent(cmd.id)
	}

	private fun KeycloakClient.createRealm(cmd: RealmCreateCommand) {
		val realms = buildRealmRepresentation(
			realm = cmd.id,
			smtpServer = cmd.smtpServer,
			theme = cmd.theme,
			locale = cmd.locale
		)
		realms().create(realms)
	}
}
