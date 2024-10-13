package io.komune.im.commons.utils

import java.io.ByteArrayOutputStream
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart

suspend fun FilePart.contentByteArray(): ByteArray {
    return ByteArrayOutputStream().use { os ->
        DataBufferUtils.write(content(), os).awaitLast()
        os.toByteArray()
    }
}
