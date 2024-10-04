package io.komune.im.bdd.spring

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.testcontainers.junit.jupiter.Testcontainers

@ExtendWith(MockitoExtension::class)
@CucumberContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(classes = [TestApplication::class])
@Testcontainers
@TestPropertySource(properties = ["logging.level.io.komune.im.infra.keycloak.client=DEBUG"])
class SpringTestConfiguration
