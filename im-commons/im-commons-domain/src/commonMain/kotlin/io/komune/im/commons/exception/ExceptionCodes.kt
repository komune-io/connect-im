@file:Suppress("FunctionOnlyReturningConstant")
package io.komune.im.commons.exception

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("ExceptionCodes")
object ExceptionCodes {
    fun privilegeWrongTarget() = 1000
}
