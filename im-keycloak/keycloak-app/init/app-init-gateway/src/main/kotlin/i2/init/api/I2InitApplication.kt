package i2.init.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["i2.init"])
class I2InitApplication

fun main(args: Array<String>) {
	SpringApplication(I2InitApplication::class.java).run {
		run(*args)
	}
}
