package city.smartb.im.commons.error

import f2.dsl.cqrs.error.Error
import f2.dsl.cqrs.error.ErrorSeverity
import f2.dsl.cqrs.error.ErrorSeverityError
import kotlinx.datetime.Clock

open class ImError(
	type: String,
	description: String,
	payload: Map<String, String>,
	date: String = Clock.System.now().toString(),
	severity: ErrorSeverity = ErrorSeverityError()
): Error<Map<String, String>>(type, description, date, payload, severity)
