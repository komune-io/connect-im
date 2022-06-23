package city.smartb.im.api.gateway

import city.smartb.im.api.auth.ImConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(ImConfig::class)
@Configuration(proxyBeanMethods = false)
@SpringBootApplication(scanBasePackages = ["city.smartb.im", "city.smartb.i2.spring.boot.auth"])
class ImApplication

fun main(args: Array<String>) {
	runApplication<ImApplication>(*args)
}
