package io.komune.im.script.core.model

import io.komune.im.commons.model.ClientIdentifier

data class ClientCredentials(
    val clientId: ClientIdentifier?,
    val clientSecret: String
)

const val DEFAULT_ROOT_CLIENT_ID = "im-root-client"

fun defaultSpaceRootClientId(
    spaceName: String
) = "${spaceName}-im-root-client"
