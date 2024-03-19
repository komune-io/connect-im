package io.komune.im.keycloak.plugin.domain.model

import org.keycloak.events.Details
import org.keycloak.events.EventType

data class KeycloakHttpEvent(
    val id: String,
    val time: Long,
    val type: EventType,
    val realmId: String,
    val clientId: String,
    val userId: String?,
    val sessionId: String?,
    val error: String?,
    val details: Map<String, String>?
) {
    fun isVerifyEmail() = type == EventType.VERIFY_EMAIL
            || type == EventType.CUSTOM_REQUIRED_ACTION && details?.get(Details.CUSTOM_REQUIRED_ACTION) == "VERIFY_EMAIL"
}
