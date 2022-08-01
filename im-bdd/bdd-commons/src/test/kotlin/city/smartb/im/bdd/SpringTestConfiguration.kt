package city.smartb.im.bdd

import city.smartb.im.api.config.bean.ImAuthenticationProvider
import city.smartb.im.api.config.properties.ImProperties
import i2.keycloak.master.domain.AuthRealm
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@ExtendWith(MockitoExtension::class)
@CucumberContextConfiguration
@ActiveProfiles("test")
@SpringBootTest(classes = [TestApplication::class])
class SpringTestConfiguration