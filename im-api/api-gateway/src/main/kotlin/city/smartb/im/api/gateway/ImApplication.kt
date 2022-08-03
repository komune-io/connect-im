package city.smartb.im.api.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

@EnableCaching
@SpringBootApplication(scanBasePackages = ["city.smartb.im", "city.smartb.i2.spring.boot.auth"])
class ImApplication

fun main(args: Array<String>) {
	SpringApplication(ImApplication::class.java).run {
//		setAdditionalProfiles("local")
		run(*args)
	}
}
