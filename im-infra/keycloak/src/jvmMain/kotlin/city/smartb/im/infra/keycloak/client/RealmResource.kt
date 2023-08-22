package city.smartb.im.infra.keycloak.client

import city.smartb.im.infra.keycloak.RealmId
import org.keycloak.admin.client.resource.RealmResource
import org.keycloak.admin.client.resource.RealmsResource
import org.keycloak.representations.idm.RealmRepresentation

fun KeycloakClient.realmsResource(): RealmsResource = keycloak.realms()
fun KeycloakClient.realmsResource(realmId: RealmId): RealmResource = keycloak.realms().realm(realmId)

const val ACCESS_TOKEN_LIFESPAN = 28800
const val SSO_SESSSION_IDLE_TIMEOUT = 604800
const val SSO_SESSION_MAX_LIFESPAN = 259200
const val ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN = 900
const val IS_VERIFY_EMAIL = true
const val ID_RESET_PASSWORD_ALLOWED = true
const val PASSWORD_POLICY = "length(8)"
const val ACCOUNT_THEME = "keycloak"
const val ADMIN_THEME = "keycloak"
const val IS_INTERNATIONALIZATION_ENABLED = true

fun KeycloakClient.buildRealmRepresentation(
	realm: String,
	smtpServer: Map<String, String>? = null,
	theme: String? = null,
	locale: String? = null,
): RealmRepresentation {
	val realms = RealmRepresentation()
	realms.realm = realm
	realms.accessTokenLifespan = ACCESS_TOKEN_LIFESPAN
	realms.ssoSessionIdleTimeout = SSO_SESSSION_IDLE_TIMEOUT
	realms.ssoSessionMaxLifespan = SSO_SESSION_MAX_LIFESPAN
	realms.actionTokenGeneratedByUserLifespan = ACTION_TOKEN_GENERATED_BY_USER_LIFESPAN
	realms.isVerifyEmail = IS_VERIFY_EMAIL
	realms.isResetPasswordAllowed = ID_RESET_PASSWORD_ALLOWED
	realms.passwordPolicy = PASSWORD_POLICY
	realms.smtpServer = smtpServer
	realms.loginTheme = theme
	realms.accountTheme = "keycloak"
	realms.adminTheme = "keycloak"
	realms.emailTheme = theme
	realms.isInternationalizationEnabled = IS_INTERNATIONALIZATION_ENABLED

	realms.supportedLocales = setOf(theme)
	realms.defaultLocale = locale

	realms.isEnabled = true
	return realms
}
