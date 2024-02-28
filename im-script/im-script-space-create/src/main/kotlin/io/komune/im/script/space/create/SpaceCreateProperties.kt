package io.komune.im.script.space.create

import io.komune.im.commons.model.SpaceIdentifier

data class SpaceCreateProperties(
    val space: SpaceIdentifier,
    val theme: String?,
    val smtp: Map<String, String>?,
    val locales: List<String>?,
    val adminUsers: List<AdminUserData>
)

data class AdminUserData(
    val email: String,
    val password: String?,
    val username: String?,
    val firstName: String?,
    val lastName: String?
)
