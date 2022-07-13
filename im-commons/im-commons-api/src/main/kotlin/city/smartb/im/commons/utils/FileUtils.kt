package city.smartb.im.commons.utils

import kotlinx.coroutines.reactive.awaitLast
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import java.io.ByteArrayOutputStream

suspend fun FilePart.contentByteArray(): ByteArray {
    return ByteArrayOutputStream().use { os ->
        DataBufferUtils.write(content(), os).awaitLast()
        os.toByteArray()
    }
}
