package city.smartb.im.test.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["city.smartb.im"])
class TestApp

fun main(args: Array<String>) {
    runApplication<TestApp>(*args)
}
