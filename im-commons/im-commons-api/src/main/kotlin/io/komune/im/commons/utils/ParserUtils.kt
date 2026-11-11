package io.komune.im.commons.utils

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.JarURLConnection
import java.net.MalformedURLException
import java.net.URL
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
            return readFile(filename = configPath, classLoader = classLoader) { stream -> stream.parseTo(clazz) }
                ?: error("Configuration file [$configPath] not found")
        } catch (e: IOException) {
            val message = "Error configuration from json file [${configPath}]"
            logger.error(message, e)
            throw IllegalStateException(message, e)
        }
    }

    fun <T> getConfiguration(
        configPath: String,
        clazz: Class<Array<T>>,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader
    ): List<T> {
        try {
            logger.info("Loading configuration from json file [$configPath]...")
            return readFile(filename = configPath, classLoader = classLoader) { stream -> stream.parseJsonTo(clazz) }
                ?: error("Configuration file [$configPath] not found")
        } catch (e: IOException) {
            val message = "Error configuration from json file [${configPath}]"
            logger.error(message, e)
            throw IllegalStateException(message, e)
        }
    }

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
