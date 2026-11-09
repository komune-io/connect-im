package io.komune.im.f2.privilege.domain.role.model

import kotlin.js.JsExport

@JsExport
object RoleTargetValues {
    const val ORGANIZATION = "ORGANIZATION"
    const val USER = "USER"
    const val API_KEY = "API_KEY"

    fun organization(): String = ORGANIZATION
    fun user(): String = USER
    fun apiKey(): String = API_KEY
}
