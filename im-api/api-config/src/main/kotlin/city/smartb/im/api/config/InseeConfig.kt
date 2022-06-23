package city.smartb.im.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class InseeConfig {
    @Value("\${i2.organization.insee.sirene-api}")
    lateinit var sireneApi: String

    @Value("\${i2.organization.insee.token}")
    lateinit var token: String
}
