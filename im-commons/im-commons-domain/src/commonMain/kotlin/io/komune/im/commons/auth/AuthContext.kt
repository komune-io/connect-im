package io.komune.im.commons.auth

import io.komune.im.commons.model.AuthRealm
import io.komune.im.commons.model.AuthRealmClientSecret
import io.komune.im.commons.model.AuthRealmPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

data class AuthContext(
    val auth: AuthRealm
): CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    companion object Key: CoroutineContext.Key<AuthContext>
}

suspend fun <R> withAuth(auth: AuthRealm, space: String? = null, block: suspend CoroutineScope.() -> R): R {
    val actualAuth = if (space == null) {
        auth
    } else {
        when (auth) {
            is AuthRealmClientSecret -> auth.copy(space = space)
            is AuthRealmPassword -> auth.copy(space = space)
        }
    }
    return withContext(AuthContext(actualAuth), block)
}
suspend fun currentAuth(): AuthRealm? = coroutineContext[AuthContext]?.auth
