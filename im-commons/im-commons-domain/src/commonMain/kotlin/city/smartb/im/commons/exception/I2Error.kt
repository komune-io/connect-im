package city.smartb.im.commons.exception


import kotlinx.datetime.Clock

open class I2Error(
	open val type: String,
	open val description: String,
	open val payload: Map<String, String>,
	open val date: String = Clock.System.now().toString(),
)
