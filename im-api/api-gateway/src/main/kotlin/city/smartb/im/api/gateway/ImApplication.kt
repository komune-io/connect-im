package city.smartb.im.api.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.im", "city.smartb.i2.spring.boot.auth"])
class ImApplication

fun main(args: Array<String>) {
	runApplication<ImApplication>(*args)
}
