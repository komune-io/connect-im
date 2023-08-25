package city.smartb.im.f2.privilege.domain.model

typealias PrivilegeId = String
typealias PrivilegeIdentifier = String

interface Privilege {
    val id: PrivilegeId
    val identifier: PrivilegeIdentifier
    val description: String
    val type: PrivilegeType
}
