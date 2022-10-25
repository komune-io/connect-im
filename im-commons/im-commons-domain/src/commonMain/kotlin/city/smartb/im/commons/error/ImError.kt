package city.smartb.im.commons.error

import f2.dsl.cqrs.error.F2Error
import kotlinx.datetime.Clock

open class ImError(
	id: String,
	timestamp: String,
	code: Int,
	message: String,
	requestId: String? = null
): F2Error(id, timestamp, code, message, requestId)
