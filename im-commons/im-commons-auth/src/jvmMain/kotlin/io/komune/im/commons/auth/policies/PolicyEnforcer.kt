package io.komune.im.commons.auth.policies

import io.komune.im.commons.auth.AuthedUser
import io.komune.im.commons.auth.AuthenticationProvider
import f2.dsl.fnc.F2Function
import f2.spring.exception.ForbiddenAccessException
import io.komune.im.commons.auth.AuthedUserDTO
import kotlinx.coroutines.flow.map

open class PolicyEnforcer {

    protected suspend fun check(action: String, hasAccess: suspend (AuthedUserDTO?) -> Boolean) = enforce { authedUser ->
        if (!hasAccess(authedUser)) {
            val userIdentity = authedUser
                ?.let { "User [${it.id}] [${it.identifier}]" }
                ?: "Unauthenticated user"
            throw ForbiddenAccessException("$userIdentity is not authorized to $action")
        }
    }

    protected suspend fun checkAuthed(action: String, hasAccess: suspend (AuthedUserDTO) -> Boolean = { true }) = check(action) { authedUser ->
        authedUser != null && hasAccess(authedUser)
    }

    protected suspend fun <R> enforce(block: suspend (AuthedUserDTO?) -> R): R {
        return block(AuthenticationProvider.getAuthedUser())
    }

    protected suspend fun <R> enforceAuthed(block: suspend (AuthedUserDTO) -> R): R = enforce { authedUser ->
        checkAuthed("")
        block(authedUser!!)
    }
}

fun <T, R> enforce(fnc: F2Function<T, R>, enforce: suspend (t: T) -> T) : F2Function<T, R> = F2Function { msg ->
    msg.map { value ->
        enforce(value)
    }.let {
        fnc.invoke(it)
    }
}

fun <T, R> verifyAfter(fnc: F2Function<T, R>, enforce: suspend (t: R) -> Unit) : F2Function<T, R> = F2Function { msg ->
    msg.let {
        fnc.invoke(it)
    }.map {
        enforce(it)
        it
    }
}

fun <T, R> verify(fnc: F2Function<T, R>, enforce: suspend (t: T) -> Unit) : F2Function<T, R> = F2Function { msg ->
    msg.map { value ->
        enforce(value)
        value
    }.let {
        fnc.invoke(it)
    }
}

fun <T, R> f2Function(fnc: suspend (t: T) -> R): F2Function<T, R> = F2Function { msg ->
    msg.map { value ->
        fnc(value)
    }
}
