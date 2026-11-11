package io.komune.im.infra.keycloak

import f2.spring.exception.ConflictException
import f2.spring.exception.NotFoundException
import io.komune.im.commons.exception.IMApiError
import io.komune.im.commons.exception.asException
import jakarta.ws.rs.core.Response
import org.apache.http.HttpStatus

fun Response.toEntityCreatedId(): String = this.location.toString().substringAfterLast("/")

fun Response.isFailure(): Boolean = this.status < HttpStatus.SC_OK || this.status >= HttpStatus.SC_BAD_REQUEST

@Suppress("ThrowsCount")
fun Response.onCreationFailure(entityName: String = "entity") {
    val error = this.readEntity(String::class.java)
    val msg = "Error creating $entityName (code: $status) }. Cause: $error"

    when (status) {
        HttpStatus.SC_CONFLICT -> throw ConflictException(entity = entityName, property = "", value = "")
        HttpStatus.SC_NOT_FOUND -> throw NotFoundException(name = entityName, id = "")
        else -> throw IMApiError(
            description = msg,
            payload = emptyMap()
        ).asException()
    }
}

fun Response.handleResponseError(entityName: String): String {
    if (isFailure()) {
        onCreationFailure(entityName)
    }
    return toEntityCreatedId()
}
