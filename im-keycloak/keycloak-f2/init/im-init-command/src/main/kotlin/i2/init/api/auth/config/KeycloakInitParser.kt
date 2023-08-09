package i2.init.api.auth.config

import city.smartb.im.commons.utils.ParserUtils
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory

class KeycloakInitParser {
    private val logger = LoggerFactory.getLogger(KeycloakInitParser::class.java)

     fun getConfigurations(configPath: String): List<KeycloakInitProperties>
        = ParserUtils.getConfiguration(configPath, Array<KeycloakInitProperties>::class.java)

}
