package io.komune.im.commons.utils

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import kotlin.system.exitProcess
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

const val FILE = "file:"

object ParserUtils {
    private val logger = LoggerFactory.getLogger(ParserUtils::class.java)

    fun <T> getConfiguration(
        configPath: String,
        clazz: Class<T>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): T {
        try {
            logger.info("Loading configuration from json file [$configPath]...")
            return getFile(configPath, classLoader)?.parseTo(clazz) ?: exitProcess(-1)
        } catch (e: Exception) {
            logger.error("Error configuration from json file [${configPath}]", e)
            exitProcess(-1)
        }
    }

    fun <T> getConfiguration(
        configPath: String,
        clazz: Class<Array<T>>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): List<T> {
        try {
            logger.info("Loading configuration from json file [$configPath]...")
            return getFile(configPath, classLoader)?.parseJsonTo(clazz) ?: exitProcess(-1)
        } catch (e: Exception) {
            logger.error("Error configuration from json file [${configPath}]", e)
            exitProcess(-1)
        }
    }

    @Throws(MalformedURLException::class)
    fun getFile(
        filename: String,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): InputStream? {
        val url = getUrl(filename, classLoader)
        if (url.toString().startsWith("jar:")) {
            return fileFromJar(url)
        }
        return File(url.file).inputStream()
    }

    private fun getUrl(filename: String, classLoader: ClassLoader = Thread.currentThread().contextClassLoader): URL {
        return if (filename.startsWith(FILE)) {
            URL(filename)
        } else {
            ClassPathResource(filename, classLoader).url
        }
    }


    private fun fileFromJar(file: URL): InputStream? {
        val path = file.toString()
        val jsonPath = path.substringAfter("!/")
        val jarConnection = file.openConnection() as java.net.JarURLConnection
        val jarFile = jarConnection.jarFile

        // Look for the file inside the JAR
        val jarEntry = jarFile.getJarEntry(jsonPath)

        jarEntry?.let {
            // Open stream and read contents
            return jarFile.getInputStream(it)
        }

        return null
    }

    private fun <T> String.parseTo(targetClass: Class<T>): T {
        val mapper = jacksonObjectMapper()
            .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return mapper.readValue(this, targetClass)
    }
}
