package city.smartb.im.commons.error

class ImApiError(
	id: String,
	timestamp: String,
	code: Int,
	message: String,
	requestId: String? = null
): ImError(
	id, timestamp, code, message, requestId
)
