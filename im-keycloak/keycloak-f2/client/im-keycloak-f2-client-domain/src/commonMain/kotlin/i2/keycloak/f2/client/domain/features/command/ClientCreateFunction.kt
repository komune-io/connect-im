package i2.keycloak.f2.client.domain.features.command

import city.smartb.im.commons.auth.RealmId
import city.smartb.im.infra.keycloak.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.fnc.F2Function
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import kotlin.js.JsExport
import kotlin.js.JsName

typealias ClientCreateFunction = F2Function<ClientCreateCommand, ClientCreatedEvent>

@JsExport
@JsName("ClientCreateCommand")
class ClientCreateCommand(
    val auth: AuthRealm,
    val realmId: RealmId,
    val clientIdentifier: ClientIdentifier,
    val secret: String? = null,
    val isPublicClient: Boolean = true,
    val isDirectAccessGrantsEnabled: Boolean = true,
    val isServiceAccountsEnabled: Boolean = true,
    val authorizationServicesEnabled: Boolean = false,
    val isStandardFlowEnabled: Boolean = false,
    val rootUrl: String? = null,
    val redirectUris: List<String> = emptyList(),
    val baseUrl: String = "",
    val adminUrl: String = "",
    val webOrigins: List<String> = emptyList(),
    val protocolMappers: Map<String, String> = emptyMap(),
): Command

@JsExport
@JsName("ClientCreatedEvent")
class ClientCreatedEvent(
	val id: ClientId
): Event
