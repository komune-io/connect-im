package city.smartb.im.script.space.create.config

import city.smartb.im.f2.space.domain.model.SpaceIdentifier

data class SpaceCreateProperties(
    val space: SpaceIdentifier,
    val theme: String?,
    val smtp: Map<String, String>?,
    val adminUsers: List<AdminUserData>
)

data class AdminUserData(
    val email: String,
    val password: String?,
    val username: String?,
    val firstName: String?,
    val lastName: String?
)