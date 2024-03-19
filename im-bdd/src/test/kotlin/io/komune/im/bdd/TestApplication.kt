package io.komune.im.bdd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication(scanBasePackages = ["io.komune.im", "io.komune.i2.spring.boot.auth"])
class TestApplication
