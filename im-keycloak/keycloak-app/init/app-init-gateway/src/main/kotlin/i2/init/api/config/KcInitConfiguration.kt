package i2.init.api.config

import i2.app.core.KcAuthConfiguration
import i2.app.core.KcAuthProperties
import i2.init.api.auth.KeycloakInitProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(value = [KcAuthProperties::class, KcInitProperties::class])
class KcInitConfiguration : KcAuthConfiguration() {
    @Bean
    fun keycloakInitProperties(kcInitProperties: KcInitProperties): KeycloakInitProperties {
        return KeycloakInitProperties(
                smtpConfig = kcInitProperties.smtp,
                realm = kcInitProperties.realm,
                theme = kcInitProperties.theme,
                clientId = kcInitProperties.adminClient?.name,
                clientSecret = kcInitProperties.adminClient?.secret,
                username = kcInitProperties.adminUser?.username,
                password = kcInitProperties.adminUser?.password,
                email = kcInitProperties.adminUser?.email,
                firstname = kcInitProperties.adminUser?.firstname,
                lastname = kcInitProperties.adminUser?.lastname,
                baseRoles = kcInitProperties.baseRoles
        )
    }
}
