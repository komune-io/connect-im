package i2.keycloak.f2.commons.domain

import city.smartb.im.commons.model.AuthRealm
import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Event
import f2.dsl.cqrs.Query
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("KeycloakF2Message")
interface KeycloakF2Message {
    val auth: AuthRealm
}

@JsExport
@JsName("KeycloakF2Query")
interface KeycloakF2Query: Query, KeycloakF2Message {
    override val auth: AuthRealm
}

@JsExport
@JsName("KeycloakF2Command")
interface KeycloakF2Command: Command, KeycloakF2Message {
    override val auth: AuthRealm
}

@JsExport
@JsName("KeycloakF2Result")
interface KeycloakF2Result: Event
