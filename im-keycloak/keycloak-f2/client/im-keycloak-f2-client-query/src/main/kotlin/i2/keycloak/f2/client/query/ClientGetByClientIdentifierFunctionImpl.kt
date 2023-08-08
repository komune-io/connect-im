package i2.keycloak.f2.client.query

import f2.dsl.fnc.f2Function
import i2.keycloak.f2.client.domain.ClientModel
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierFunction
import i2.keycloak.f2.client.domain.features.query.ClientGetByClientIdentifierResult
import i2.keycloak.f2.commons.app.asI2Exception
import i2.keycloak.realm.client.config.AuthRealmClientBuilder
import javax.ws.rs.NotFoundException
import org.keycloak.representations.idm.ClientRepresentation
import org.springframework.context.annotation.Bean

class ClientGetByClientIdentifierFunctionImpl {
    @Bean
    fun clientGetByClientIdentifierFunction(): ClientGetByClientIdentifierFunction = f2Function { cmd ->
        val realmClient = AuthRealmClientBuilder().build(cmd.auth)
        try {
            realmClient.getRealmResource(cmd.realmId).clients()
                .findByClientId(cmd.clientIdentifier)
                .firstOrNull()
                ?.asModel()
                .asResult()
        } catch (e: NotFoundException) {
            ClientGetByClientIdentifierResult(null)
        } catch (e: Exception) {
            throw e.asI2Exception("Error fetching client with client identifier[${cmd.clientIdentifier}]")
        }
    }

    private fun ClientRepresentation.asModel(): ClientModel {
        return ClientModel(
            id = this.id,
            clientIdentifier = this.clientId
        )
    }

    private fun ClientModel?.asResult(): ClientGetByClientIdentifierResult {
        return ClientGetByClientIdentifierResult(this)
    }
}
