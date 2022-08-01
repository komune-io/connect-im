package city.smartb.im.api.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@SpringBootApplication(scanBasePackages = ["city.smartb.im", "city.smartb.i2.spring.boot.auth"])
class ImApplication

fun main(args: Array<String>) {
	SpringApplication(ImApplication::class.java).run {
		run(*args)
	}
}
