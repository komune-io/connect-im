package i2.keycloak.f2.commons.domain.error

class I2Exception(
	val error: I2Error,
	val from: Throwable? = null
): Exception(error.description, from)

fun I2Error.asI2Exception(from: Throwable? = null): I2Exception = I2Exception(this, from)
