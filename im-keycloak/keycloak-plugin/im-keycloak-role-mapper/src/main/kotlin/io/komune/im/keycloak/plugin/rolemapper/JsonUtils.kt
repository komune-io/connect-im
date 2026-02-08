package io.komune.im.keycloak.plugin.rolemapper

import tools.jackson.core.json.JsonReadFeature
import tools.jackson.databind.DeserializationFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder

fun <T> String.parseJsonTo(targetClass: Class<T>): T {
    return this.parseTo(targetClass)
}

inline fun <reified T> String.parseJson(): T {
    val mapper = jacksonMapperBuilder()
        .enable(JsonReadFeature.ALLOW_UNQUOTED_PROPERTY_NAMES)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build()

    return mapper.readValue(this, T::class.java)
}

fun <T> String.parseJsonTo(targetClass: Class<Array<T>>): List<T> {
    return parseTo(targetClass).toList()
}

private fun <T> String.parseTo(targetClass: Class<T>): T {
    val mapper = jacksonMapperBuilder()
        .enable(JsonReadFeature.ALLOW_UNQUOTED_PROPERTY_NAMES)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build()

    return mapper.readValue(this, targetClass)
}

fun <T> T.toJson(): String {
    val mapper = jacksonMapperBuilder().build()

    return mapper.writeValueAsString(this)
}
