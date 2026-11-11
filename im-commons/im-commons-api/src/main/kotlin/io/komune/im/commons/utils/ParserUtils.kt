package io.komune.im.commons.utils

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.JarURLConnection
import java.net.MalformedURLException
import java.net.URL
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import tools.jackson.core.JacksonException

const val FILE = "file:"

object ParserUtils {
    private val logger = LoggerFactory.getLogger(ParserUtils::class.java)

    fun <T> getConfiguration(
        configPath: String,
        clazz: Class<T>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): T = readConfiguration(configPath) {
        readFile(filename = configPath, classLoader = classLoader) { stream -> stream.parseTo(clazz) }
    }

    fun <T> getConfiguration(
        configPath: String,
        clazz: Class<Array<T>>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): List<T> = readConfiguration(configPath) {
        readFile(filename = configPath, classLoader = classLoader) { stream -> stream.parseJsonTo(clazz) }
    }

    private fun <R> readConfiguration(configPath: String, read: () -> R?): R {
        logger.info("Loading configuration from json file [$configPath]...")
        val configuration = try {
            read()
        } catch (e: IOException) {
            unreadable(configPath, e)
        } catch (e: JacksonException) {
            unreadable(configPath, e)
        }
        return configuration ?: notFound(configPath)
    }

    private fun unreadable(configPath: String, cause: Exception): Nothing {
        val message = "Error configuration from json file [${configPath}]"
        logger.error(message, cause)
        throw ConfigurationException(message, cause)
    }

    private fun notFound(configPath: String): Nothing =
        throw ConfigurationException("Configuration file [$configPath] not found")

    @Throws(MalformedURLException::class)
    private fun <R> readFile(
        filename: String,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader,
        read: (InputStream) -> R
    ): R? {
        val url = getUrl(filename, classLoader)
        if (url.toString().startsWith("jar:")) {
            return readFileFromJar(file = url, read = read)
        }
        return File(url.file).inputStream().use(read)
    }

    private fun getUrl(filename: String, classLoader: ClassLoader = Thread.currentThread().contextClassLoader): URL =
        if (filename.startsWith(FILE)) {
            URL(filename)
        } else {
            ClassPathResource(filename, classLoader).url
        }


    private fun <R> readFileFromJar(file: URL, read: (InputStream) -> R): R? {
        val path = file.toString()
        val jsonPath = path.substringAfter("!/")
        val jarConnection = file.openConnection() as JarURLConnection
        val jarFile = jarConnection.jarFile

        // Look for the file inside the JAR
        val jarEntry = jarFile.getJarEntry(jsonPath) ?: return null

        return jarFile.getInputStream(jarEntry).use(read)
    }
}

/** A configuration file that is missing or cannot be read: retrying cannot fix it. */
class ConfigurationException(message: String, cause: Throwable? = null) : IllegalStateException(message, cause)
