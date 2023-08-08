package i2.keycloak.f2.client.query

import i2.keycloak.f2.client.domain.ClientModel
import org.keycloak.representations.idm.ClientRepresentation

fun List<ClientRepresentation>.asModels(): List<ClientModel> = map(ClientRepresentation::asModel)

fun ClientRepresentation.asModel(): ClientModel {
	return ClientModel(
		id = this.id,
		clientIdentifier = this.clientId
	)
}
