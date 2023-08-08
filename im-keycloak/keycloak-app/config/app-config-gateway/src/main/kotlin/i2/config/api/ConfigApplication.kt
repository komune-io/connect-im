package i2.config.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@EntityScan("i2.config")
@SpringBootApplication(scanBasePackages = ["i2.config"])
class ConfigApplication

fun main(args: Array<String>) {
	SpringApplication(ConfigApplication::class.java).run {
		run(*args)
	}
}
