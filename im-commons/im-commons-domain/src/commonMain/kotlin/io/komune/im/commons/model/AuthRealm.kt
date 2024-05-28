package io.komune.im.commons.model

import f2.client.ktor.http.plugin.model.AuthRealm

data class AuthSubRealm(
    val master: AuthRealm,
    val space: String
)
