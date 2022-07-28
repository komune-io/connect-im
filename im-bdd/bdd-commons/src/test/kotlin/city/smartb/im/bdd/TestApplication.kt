package city.smartb.im.bdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EntityScan("city.smartb.im")
@SpringBootApplication(scanBasePackages = ["city.smartb.im"])
//@Import(PostgresConfiguration::class)
class TestApplication
