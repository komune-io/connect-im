package io.komune.im.commons.exception

data class I2ApiError(
	override val description: String,
	override val payload: Map<String, String>,
): IMError(
	type = I2ApiError::class.simpleName!!,
	description = description,
	payload = payload,
)
