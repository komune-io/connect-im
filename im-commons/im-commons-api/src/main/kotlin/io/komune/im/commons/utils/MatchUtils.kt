package io.komune.im.commons.utils

fun <T> T.matches(filter: T?): Boolean {
    return filter == null || this == filter
}

fun <T> T.matches(filter: Collection<T>?): Boolean {
    return filter == null || this in filter
}

fun <T> Collection<T>.matches(filter: Collection<T>?): Boolean {
    return filter == null || this.any { it.matches(filter) }
}
