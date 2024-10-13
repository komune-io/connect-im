package io.komune.im.commons.model

import f2.client.domain.AuthRealm

data class AuthSubRealm(
    val master: AuthRealm,
    val space: String
)
