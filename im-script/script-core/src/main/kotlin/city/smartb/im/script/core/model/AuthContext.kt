package city.smartb.im.script.core.model

import city.smartb.im.commons.model.AuthRealm
import kotlin.coroutines.CoroutineContext

class AuthContext(
    val auth: AuthRealm
): CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    companion object Key: CoroutineContext.Key<AuthContext>
}
