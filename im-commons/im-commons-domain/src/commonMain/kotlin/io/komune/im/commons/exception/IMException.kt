package io.komune.im.commons.exception

class IMException(
    val error: IMError,
    val from: Throwable? = null
): Exception(error.description, from)

fun IMError.asException(from: Throwable? = null): IMException = IMException(this, from)
