package i2.keycloak.f2.commons.domain.error

class I2ApiError(
	override val description: String,
	override val payload: Map<String, String>,
): I2Error(
	type = I2ApiError::class.simpleName!!,
	description = description,
	payload = payload,
)
