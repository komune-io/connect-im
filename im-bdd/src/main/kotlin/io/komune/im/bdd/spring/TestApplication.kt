package io.komune.im.bdd.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication(scanBasePackages = ["io.komune.im"])
class TestApplication
