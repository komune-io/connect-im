package io.komune.im.commons.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

suspend fun <T, R> Collection<T>.mapAsyncDeferred(transform: suspend (T) -> R): List<Deferred<R>> = coroutineScope {
    map { item ->
        async {
            transform(item)
        }
    }
}

suspend fun <T, R> Collection<T>.mapNotNullAsyncDeferred(transform: suspend (T) -> R?): List<Deferred<R?>> =
    coroutineScope {
        mapNotNull { item ->
            async {
                transform(item)
            }
        }
    }

suspend fun <T, R> Collection<T>.mapAsync(transform: suspend (T) -> R): List<R> = mapAsyncDeferred(transform).awaitAll()
suspend fun <T, R> Collection<T>.mapNotNullAsync(transform: suspend (T) -> R?): List<R> =
    mapAsyncDeferred(transform).awaitAll().filterNotNull()

/** Runs [action] on every element concurrently, and returns once all of them are done. */
suspend fun <T> Collection<T>.forEachAsync(action: suspend (T) -> Unit): Unit = coroutineScope {
    forEach { launch { action(it) } }
}
