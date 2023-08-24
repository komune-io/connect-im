package city.smartb.im.bdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching


@EnableCaching
@SpringBootApplication(scanBasePackages = ["city.smartb.im", "city.smartb.i2.spring.boot.auth"])
class TestApplication