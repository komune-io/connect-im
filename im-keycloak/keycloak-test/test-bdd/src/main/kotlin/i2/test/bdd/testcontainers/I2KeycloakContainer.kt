package i2.test.bdd.testcontainers

import i2.test.bdd.config.KeycloakConfig
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.time.Duration

class I2KeycloakContainer(file: File): DockerComposeContainer<I2KeycloakContainer>(file) {

	init {
		withExposedService("keycloak-it",
			8080,
			Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(240)))
	}

	companion object {
		private var container: I2KeycloakContainer? = null
		fun getInstance(file: File): I2KeycloakContainer? {
			if (container == null && KeycloakConfig.mustStartDocker) {
				container = I2KeycloakContainer(file)
				container!!.start()
			}
			return container
		}
	}
}
