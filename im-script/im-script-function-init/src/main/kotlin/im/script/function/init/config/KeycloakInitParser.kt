package im.script.function.init.config

import city.smartb.im.commons.utils.ParserUtils
import org.slf4j.LoggerFactory

class KeycloakInitParser {
    private val logger = LoggerFactory.getLogger(KeycloakInitParser::class.java)

     fun getConfigurations(configPath: String): List<KeycloakInitProperties>
        = ParserUtils.getConfiguration(configPath, Array<KeycloakInitProperties>::class.java)

}
