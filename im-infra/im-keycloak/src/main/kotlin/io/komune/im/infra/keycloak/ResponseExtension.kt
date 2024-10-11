package io.komune.im.infra.keycloak

import io.komune.im.commons.exception.I2ApiError
import io.komune.im.commons.exception.asException
import f2.spring.exception.ConflictException
import f2.spring.exception.NotFoundException
import jakarta.ws.rs.core.Response
import org.apache.http.HttpStatus

fun Response.toEntityCreatedId(): String {
	return this.location.toString().substringAfterLast("/")
}

fun Response.isFailure(): Boolean {
	return this.status < HttpStatus.SC_OK || this.status >= HttpStatus.SC_BAD_REQUEST
}

@Suppress("ThrowsCount")
fun Response.onCreationFailure(entityName: String = "entity") {
	val error = this.readEntity(String::class.java)
	val msg = "Error creating $entityName (code: $status) }. Cause: $error"

    when (status) {
        HttpStatus.SC_CONFLICT -> throw ConflictException(entityName, "", "")
        HttpStatus.SC_NOT_FOUND -> throw NotFoundException(entityName, "")
        else -> throw I2ApiError(
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
