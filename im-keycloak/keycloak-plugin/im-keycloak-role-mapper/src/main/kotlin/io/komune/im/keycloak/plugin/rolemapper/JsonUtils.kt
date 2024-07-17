package io.komune.im.keycloak.plugin.rolemapper

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun <T> String.parseJsonTo(targetClass: Class<T>): T {
    return this.parseTo(targetClass)
}

inline fun <reified T> String.parseJson(): T {
    val mapper = ObjectMapper()
        .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerKotlinModule()

    return mapper.readValue(this, T::class.java)
}

fun <T> String.parseJsonTo(targetClass: Class<Array<T>>): List<T> {
    return parseTo(targetClass).toList()
}

private fun <T> String.parseTo(targetClass: Class<T>): T {
    val mapper = ObjectMapper()
            .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerKotlinModule()

    return mapper.readValue(this, targetClass)
}

fun <T> T.toJson(): String {
    val mapper = ObjectMapper()
            .registerKotlinModule()

    return mapper.writeValueAsString(this)
}
