package city.smartb.im.bdd

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@ExtendWith(MockitoExtension::class)
@CucumberContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(classes = [TestApplication::class])
class SpringTestConfiguration