package io.komune.im.keycloak.plugin.rolemapper

import tools.jackson.core.json.JsonReadFeature
import tools.jackson.databind.DeserializationFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder

private val mapper = jacksonMapperBuilder()
	.enable(JsonReadFeature.ALLOW_UNQUOTED_PROPERTY_NAMES)
	.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
	.build()

fun <T> String.parseJsonTo(targetClass: Class<Array<T>>): List<T> {
	return mapper.readValue(this, targetClass).toList()
}
