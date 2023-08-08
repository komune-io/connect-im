package i2.keycloak.f2.commons.app

import i2.keycloak.f2.commons.domain.error.I2ApiError
import i2.keycloak.f2.commons.domain.error.I2Exception
import i2.keycloak.f2.commons.domain.error.asI2Exception
import javax.ws.rs.ClientErrorException
import javax.ws.rs.NotFoundException
import javax.ws.rs.ProcessingException

fun <T> ProcessingException.handleNotFoundCause(msg: String, block: () -> T): T {
    if(cause is NotFoundException) {
        return block()
    }
    throw this.asI2Exception(msg)
}

fun Throwable.asI2Exception(msg: String): I2Exception {
    val description = if(this is ClientErrorException) {
        val responseError = response.readEntity(String::class.java)
        "$msg. Details: $responseError"
    } else if(this.cause is ClientErrorException) {
        val responseError = (this.cause as ClientErrorException).response.readEntity(String::class.java)
        "$msg. Details: $responseError"
    } else  msg

    return I2ApiError(
        description = description,
        payload = emptyMap()
    ).asI2Exception(this)
}
