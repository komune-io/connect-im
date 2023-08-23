package city.smartb.im.privilege.domain.model

typealias PrivilegeIdentifier = String

interface Privilege {
    val identifier: PrivilegeIdentifier
    val description: String
    val type: PrivilegeType
}
