package i2.config.api.auth.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import i2.config.api.auth.KeycloakConfigProperties
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory

const val FILE = "file:"

class KeycloakConfigParser {
    private val logger = LoggerFactory.getLogger(KeycloakConfigParser::class.java)

    fun getConfiguration(configPath: String): KeycloakConfigProperties {
        try {
            logger.info("Loading configuration from json file [$configPath]...")
            return getFile(configPath).readText().parseTo(KeycloakConfigProperties::class.java)
        } catch (e: Exception) {
            logger.error("Error configuration from json file [${configPath}]", e)
            exitProcess(-1)
        }
    }

    @Throws(MalformedURLException::class)
    fun getFile(filename: String): File {
        val url = getUrl(filename)
        return File(url.file)
    }

    private fun getUrl(filename: String): URL {
        return if (filename.startsWith(FILE)) {
            URL(filename)
        } else {
            Thread.currentThread().contextClassLoader.getResource(filename)!!
        }
    }

    private fun <T> String.parseTo(targetClass: Class<T>): T {
        val mapper = jacksonObjectMapper()
            .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return mapper.readValue(this, targetClass)
    }
}
