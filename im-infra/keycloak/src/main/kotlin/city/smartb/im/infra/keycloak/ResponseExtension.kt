package city.smartb.im.infra.keycloak

import f2.spring.exception.ConflictException
import f2.spring.exception.NotFoundException
import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.asI2Exception
import org.apache.http.HttpStatus
import javax.ws.rs.core.Response

fun Response.toEntityCreatedId(): String {
	return this.location.toString().substringAfterLast("/")
}

fun Response.isFailure(): Boolean {
	return this.status < HttpStatus.SC_OK || this.status >= HttpStatus.SC_BAD_REQUEST
}

fun Response.onCreationFailure(entityName: String = "entity") {
	val error = this.readEntity(String::class.java)
	val msg = "Error creating $entityName (code: $status) }. Cause: $error"

    val cause = when (status) {
        HttpStatus.SC_CONFLICT -> ConflictException(entityName, "", "")
        HttpStatus.SC_NOT_FOUND -> NotFoundException(entityName, "")
        else -> null
    }

	throw I2ApiError(
		description = msg,
		payload = emptyMap()
	).asI2Exception(cause)
}

fun Response.handleResponseError(entityName: String): String {
	if (isFailure()) {
		onCreationFailure(entityName)
	}
	return toEntityCreatedId()
}
