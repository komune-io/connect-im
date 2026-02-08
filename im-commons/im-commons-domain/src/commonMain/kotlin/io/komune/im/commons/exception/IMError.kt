package io.komune.im.commons.exception


import kotlin.time.Clock

open class IMError(
	open val type: String,
	open val description: String,
	open val payload: Map<String, String>,
	open val date: String = Clock.System.now().toString(),
)
