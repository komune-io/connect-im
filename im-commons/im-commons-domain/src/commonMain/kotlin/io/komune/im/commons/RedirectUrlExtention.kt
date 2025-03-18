package io.komune.im.commons

fun String.addWildcard(): String {
    return if(this.endsWith("/*"))
        this
    else if(this.endsWith("/")) {
        "${this}*"
    }else {
        "${this}/*"
    }
}
