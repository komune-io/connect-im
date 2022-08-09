package city.smartb.im.commons.exception

class NotFoundException(
    val name: String,
    val id: String
): ImException("$name [$id] not found")
