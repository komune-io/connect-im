package io.komune.im.script.core.config.properties

data class SpaceSettingsProperties(
    val login: LoginSettingsProperties,
)

data class LoginSettingsProperties(
    val registrationAllowed: Boolean = false,
    val resetPasswordAllowed : Boolean = true,
    val rememberMe : Boolean = false,
    val registrationEmailAsUsername : Boolean = false
)
