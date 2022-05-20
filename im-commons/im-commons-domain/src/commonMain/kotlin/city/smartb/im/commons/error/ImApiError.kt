package city.smartb.im.commons.error

import f2.dsl.cqrs.error.ErrorSeverityError

class ImApiError(
	override val description: String,
	override val payload: Map<String, String>,
): ImError(
	type = ImApiError::class.simpleName!!,
	severity = ErrorSeverityError(),
	description = description,
	payload = payload,
)
