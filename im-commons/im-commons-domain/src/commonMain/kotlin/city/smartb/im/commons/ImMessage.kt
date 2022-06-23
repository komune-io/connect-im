package city.smartb.im.commons

import f2.dsl.cqrs.Message
import i2.keycloak.master.domain.AuthRealm
import i2.keycloak.master.domain.RealmId

/**
 * Class used to add an overlay to every IM functions
 * It is used by client (remote and sdk) to inject the AuthRealm bean (defined in city.smartb.im.api.auth.ImAuthConfig)
 */
data class ImMessage<P: Message>(
    val authRealm: AuthRealm,
    val realmId: RealmId,
    val payload: P,
)