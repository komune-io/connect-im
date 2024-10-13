package io.komune.im.commons.auth

import io.komune.im.commons.model.AuthSubRealm
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

data class AuthContext(
    val auth: AuthSubRealm
): CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    companion object Key: CoroutineContext.Key<AuthContext>
}

suspend fun <R> withAuth(auth: AuthSubRealm, space: String? = null, block: suspend CoroutineScope.() -> R): R {
    return withContext(AuthContext(
        auth.copy(space = space ?: auth.space)
    ), block)
}
suspend fun currentAuth(): AuthSubRealm? = coroutineContext[AuthContext]?.auth
