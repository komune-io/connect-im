package i2.init.api.auth

data class KeycloakInitProperties(
    val smtpConfig: Map<String, String> = mutableMapOf(),
    val realm: String,
    val clientId: String? = null,
    val clientSecret: String? = null,
    val username: String? = null,
    val password: String? = null,
    val email: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val baseRoles: List<String> = mutableListOf()
)
