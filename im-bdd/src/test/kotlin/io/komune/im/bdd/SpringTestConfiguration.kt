package io.komune.im.bdd

import io.cucumber.spring.CucumberContextConfiguration
import java.io.File
import java.time.Duration
import kotlin.jvm.optionals.getOrNull
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@ExtendWith(MockitoExtension::class)
@CucumberContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(classes = [TestApplication::class])
@Testcontainers
@TestPropertySource(properties = ["logging.level.io.komune.im.infra.keycloak.client=DEBUG"])
class SpringTestConfiguration {

//    @Container
//    var i2KeycloakContainer: I2KeycloakContainer =
//        I2KeycloakContainer.getInstance(ClassPathResource("docker-compose-it.yml").file).also {
//            println( it.getContainerByServiceName("keycloak-it").getOrNull()?.isCreated)
//            println( it.getContainerByServiceName("keycloak-it").getOrNull()?.isRunning)
//        }
//    @Container
//    var init: I2InitContainer = I2InitContainer.fromClassPath("docker-compose-i2-init.yml")

}


//class I2KeycloakContainer(file: File): DockerComposeContainer<I2KeycloakContainer>(file) {
//
//    init {
//        withExposedService("keycloak-it",
//            8080,
//            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(240)))
//    }
//
//    companion object {
//        private var container: I2KeycloakContainer? = null
//        fun getInstance(file: File): I2KeycloakContainer {
//            if (container == null && KeycloakConfig.mustStartDocker) {
//                container = I2KeycloakContainer(file)
//                container!!.start()
//            }
//            return container!!
//        }
//    }
//}
//
//class I2InitContainer(file: File): DockerComposeContainer<I2InitContainer>(file) {
//
//    companion object {
//        private var container: I2InitContainer? = null
//
//        fun fromClassPath(file: String): I2InitContainer {
//            return getInstance(ClassPathResource("docker-compose-it.yml").file)
//        }
//        fun getInstance(file: File): I2InitContainer {
//            if (container == null && KeycloakConfig.mustStartDocker) {
//                container = I2InitContainer(file)
//                container!!.start()
//            }
//            return container!!
//        }
//    }
//}
//
//
//object KeycloakConfig {
//    val url: String
//        get() {
//            return "http://keycloak-it:8080".orIfGitlabEnv("KEYCLOAK_URL")
//        }
//
//    object Admin {
//
//
//        val username: String
//            get() {
//                return "admin".orIfGitlabEnv("KEYCLOAK_ADMIN_USERNAME")
//            }
//
//        val password: String
//            get() {
//                return "admin".orIfGitlabEnv("KEYCLOAK_ADMIN_PASSWORD")
//            }
//
//        val clientId: String
//            get() {
//                return "admin-cli".orIfGitlabEnv("KEYCLOAK_ADMIN_CLIENT_ID")
//            }
//    }
//
//    val mustStartDocker: Boolean
//        get() = !isGitlab()
//
//    fun String.orIfGitlabEnv(value: String): String {
//        return if (isGitlab()) {
//            println("//////////////////////////////////////////////")
//            println("//////////////////////////////////////////////")
//            println("//////////////////////////////////////////////")
//            getEnv(value)
//        } else {
//            this
//        }
//    }
//
//    private fun isGitlab() = getEnv("SPRING_PROFILES_ACTIVE") == "gitlab"
//    private fun getEnv(value: String): String {
//        val envValue = System.getenv(value)
//        return if (envValue != null) {
//            envValue
//        } else {
//            println("Env parameter[$value] is null")
//            ""
//        }
//    }
//}
