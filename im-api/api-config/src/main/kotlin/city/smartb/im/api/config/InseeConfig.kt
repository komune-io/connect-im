package city.smartb.im.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class InseeConfig {
    @Value("\${im.organization.insee.sirene-api}")
    lateinit var sireneApi: String

    @Value("\${im.organization.insee.token}")
    lateinit var token: String
}
