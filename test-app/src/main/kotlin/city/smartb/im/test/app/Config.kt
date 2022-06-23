package city.smartb.im.test.app

//import city.smartb.im.organization.client.OrganizationClient
//import city.smartb.im.organization.client.OrganizationClientSdk
//import city.smartb.im.organization.client.OrganizationClientSdkFactory
import city.smartb.im.organization.client.OrganizationClientRemote
import i2.keycloak.master.domain.AuthRealm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config(
    private val tokenProvider: TokenProvider,
    private val authRealm: AuthRealm,
//    private val organizationClientSdkFactory: OrganizationClientSdkFactory
) {
    @Value("\${im}")
    lateinit var imUrl: String

    /**
     * Bean for remote client
     */
    @Bean
    fun organizationClient() = OrganizationClientRemote(
        url = imUrl,
        generateBearerToken = tokenProvider::getToken,
        authRealm = authRealm
    )

    /**
     * Bean for sdk client
     */
//    @Bean
//    fun organizationClient(): OrganizationClientSdk = organizationClientSdkFactory.build(authRealm)

}
