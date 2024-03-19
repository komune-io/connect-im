package io.komune.im.api.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication(scanBasePackages = ["io.komune.im", "io.komune.i2.spring.boot.auth"])
class ImApplication

fun main(args: Array<String>) {
	SpringApplication(ImApplication::class.java).run {
//		setAdditionalProfiles("local")
		run(*args)
	}
}
