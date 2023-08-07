package i2.test.bdd.testcontainers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@EntityScan("i2")
@SpringBootApplication(scanBasePackages = ["i2"])
class I2KeycloakTestApplication

fun main(args: Array<String>) {
	runApplication<I2KeycloakTestApplication>(*args)
}
