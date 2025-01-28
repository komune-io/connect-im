package io.komune.im.commons.exception

data class IMApiError(
	override val description: String,
	override val payload: Map<String, String>,
): IMError(
	type = IMApiError::class.simpleName!!,
	description = description,
	payload = payload,
)
