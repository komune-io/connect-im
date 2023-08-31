package city.smartb.im.core.client.domain.model

typealias ClientId = String
typealias ClientIdentifier = String

data class Client(
    val id: ClientId,
    val identifier: ClientIdentifier,
    val isDirectAccessGrantsEnabled: Boolean,
    val isServiceAccountsEnabled: Boolean,
    val authorizationServicesEnabled: Boolean,
    val isStandardFlowEnabled: Boolean,
    val isPublicClient: Boolean,
    val rootUrl: String?,
    val redirectUris: List<String>,
    val baseUrl: String,
    val adminUrl: String,
    val webOrigins: List<String>
)
