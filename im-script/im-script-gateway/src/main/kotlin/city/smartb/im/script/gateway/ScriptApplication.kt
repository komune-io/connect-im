package city.smartb.im.script.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@SpringBootApplication(scanBasePackages = ["city.smartb.im.script"])
class ScriptApplication

fun main(args: Array<String>) {
	SpringApplication(ScriptApplication::class.java).run {
//        setAdditionalProfiles("local")
		run(*args)
	}
}
