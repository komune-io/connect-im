package city.smartb.im.commons.exception

data class I2ApiError(
	override val description: String,
	override val payload: Map<String, String>,
): I2Error(
	type = I2ApiError::class.simpleName!!,
	description = description,
	payload = payload,
)
