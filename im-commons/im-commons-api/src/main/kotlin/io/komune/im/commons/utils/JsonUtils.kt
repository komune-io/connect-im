package io.komune.im.commons.utils

import tools.jackson.core.json.JsonReadFeature
import tools.jackson.databind.DeserializationFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder
import java.io.InputStream

val mapper = jacksonMapperBuilder()
    .enable(JsonReadFeature.ALLOW_UNQUOTED_PROPERTY_NAMES)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .build()


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
    return mapper.readValue(this, targetClass)
}


fun <T> InputStream.parseJsonTo(targetClass: Class<Array<T>>): List<T> {
    return parseTo(targetClass).toList()
}

fun <T> InputStream.parseTo(targetClass: Class<T>): T {
    return mapper.readValue(this, targetClass)
}

fun <T> T.toJson(): String {
    val mapper = jacksonMapperBuilder().build()

    return mapper.writeValueAsString(this)
}
