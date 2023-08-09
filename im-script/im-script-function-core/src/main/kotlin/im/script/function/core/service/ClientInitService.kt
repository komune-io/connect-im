package im.script.function.core.service

import f2.dsl.fnc.invokeWith
import i2.keycloak.f2.client.domain.ClientId
import i2.keycloak.f2.client.domain.ClientIdentifier
import i2.keycloak.f2.client.domain.features.command.ClientCreateCommand
import i2.keycloak.f2.client.domain.features.command.ClientCreateFunction
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantCommand
import i2.keycloak.f2.client.domain.features.command.ClientRealmManagementRolesGrantFunction
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantCommand
import i2.keycloak.f2.client.domain.features.command.ClientServiceAccountRolesGrantFunction
import i2.keycloak.f2.role.domain.RoleName
import i2.keycloak.master.domain.AuthRealm
import im.script.function.core.model.AppClient
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ClientInitService(
    private val scriptFinderService: ScriptFinderService,
    private val clientCreateFunction: ClientCreateFunction,
    private val clientServiceAccountRolesGrantFunction: ClientServiceAccountRolesGrantFunction,
    private val clientRealmManagementRolesGrantFunction: ClientRealmManagementRolesGrantFunction,
) {

    private val logger = LoggerFactory.getLogger(ClientInitService::class.java)

    suspend fun initAppClient(authRealm: AuthRealm, appClient: AppClient) {
        if (!checkIfExists(authRealm, appClient.clientId)) {
            val secret = appClient.clientSecret ?: UUID.randomUUID().toString()
            createClient(
                authRealm = authRealm,
                identifier = appClient.clientId,
                secret = secret,
                isPublic = false,
                isServiceAccountsEnabled = true,
                isDirectAccessGrantsEnabled = false,
                isStandardFlowEnabled = false
            ).let { clientId ->
                appClient.roles?.toList()?.let { list ->
                    grantClient(
                        authRealm = authRealm,
                        id = clientId,
                        roles = list
                    )
                }
                appClient.realmManagementRoles?.toList()?.let { list ->
                    grantRealmManagementClient(
                        authRealm = authRealm,
                        id = appClient.clientId,
                        roles = list,
                    )
                }
            }
            logger.info("App secret: $secret")
        }
    }


    suspend fun grantClient(authRealm: AuthRealm, id: ClientId, roles: List<RoleName>) {
        ClientServiceAccountRolesGrantCommand(
            id = id,
            roles = roles,
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(clientServiceAccountRolesGrantFunction)
    }

    suspend fun grantRealmManagementClient(authRealm: AuthRealm, id: ClientId, roles: List<RoleName>) {
        ClientRealmManagementRolesGrantCommand(
            id = id,
            roles = roles,
            auth = authRealm,
            realmId = authRealm.realmId
        ).invokeWith(clientRealmManagementRolesGrantFunction)
    }

    suspend fun createClient(
        authRealm: AuthRealm,
        identifier: ClientIdentifier,
        secret: String? = null,
        isPublic: Boolean = true,
        isDirectAccessGrantsEnabled: Boolean = true,
        isServiceAccountsEnabled: Boolean = true,
        authorizationServicesEnabled: Boolean = false,
        isStandardFlowEnabled: Boolean = false,
        baseUrl: String? = null,
        protocolMappers: Map<String, String> = emptyMap(),
    ): ClientId {
        return ClientCreateCommand(
            auth = authRealm,
            realmId = authRealm.realmId,
            clientIdentifier = identifier,
            secret = secret,
            isPublicClient = isPublic,
            isDirectAccessGrantsEnabled = isDirectAccessGrantsEnabled,
            isServiceAccountsEnabled = isServiceAccountsEnabled,
            authorizationServicesEnabled = authorizationServicesEnabled,
            isStandardFlowEnabled = isStandardFlowEnabled,
            rootUrl = baseUrl,
            redirectUris = listOfNotNull(baseUrl),
            baseUrl = baseUrl ?: "",
            adminUrl = baseUrl ?: "",
            webOrigins = listOfNotNull(baseUrl),
            protocolMappers = protocolMappers,
        ).invokeWith(clientCreateFunction).id
    }

    private suspend fun checkIfExists(authRealm: AuthRealm, clientId: ClientId): Boolean {
        return if (scriptFinderService.getClient(authRealm, clientId) != null) {
            logger.info("Client [$clientId] already exists.")
            true
        } else {
            false
        }
    }
}
