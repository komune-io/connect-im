package city.smartb.im.f2.privilege.domain.model

import kotlin.js.JsExport

typealias PrivilegeId = String
typealias PrivilegeIdentifier = String

/**
 * @d2 hidden
 */
@JsExport
interface PrivilegeDTO {
    /**
     * Generated id of the privilege.
     */
    val id: PrivilegeId

    /**
     * Identifier of the privilege. Must be unique within a realm.
     * @example "tr_orchestrator"
     */
    val identifier: PrivilegeIdentifier

    /**
     * Description of the privilege.
     * @example "Main organization role for orchestrators"
     */
    val description: String

    /**
     * Type of privilege. See [PrivilegeType][city.smartb.im.core.privilege.domain.model.PrivilegeType]
     * @example "ROLE"
     */
    val type: String
}
