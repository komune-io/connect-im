package city.smartb.im.commons.auth.policies

import city.smartb.im.commons.auth.AuthedUser
import city.smartb.im.commons.auth.AuthenticationProvider
import f2.dsl.fnc.F2Function
import f2.spring.exception.ForbiddenAccessException
import kotlinx.coroutines.flow.map

open class PolicyEnforcer {

    protected suspend fun check(action: String, hasAccess: suspend (AuthedUser?) -> Boolean) = enforce { authedUser ->
        if (!hasAccess(authedUser)) {
            throw ForbiddenAccessException(action)
        }
    }

    protected suspend fun checkAuthed(action: String, hasAccess: suspend (AuthedUser) -> Boolean = { true }) = check(action) { authedUser ->
        authedUser != null && hasAccess(authedUser)
    }

    protected suspend fun <R> enforce(block: suspend (AuthedUser?) -> R): R {
        return block(AuthenticationProvider.getAuthedUser())
    }

    protected suspend fun <R> enforceAuthed(block: suspend (AuthedUser) -> R): R = enforce { authedUser ->
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
