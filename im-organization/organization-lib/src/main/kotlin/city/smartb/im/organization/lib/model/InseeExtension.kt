package city.smartb.im.organization.lib.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.toJson
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.organization.lib.model.insee.InseeAddress
import city.smartb.im.organization.lib.model.insee.InseeOrganization
import i2.keycloak.f2.role.domain.RolesCompositesModel

fun InseeOrganization.toOrganization() = Organization(
    id = "",
    siret = siret,
    name = uniteLegale?.denominationUniteLegale.orEmpty(),
    description = null,
    address = adresseEtablissement?.toAddress(),
    website = null,
    roles = emptyList(),
    rolesComposites = RolesCompositesModel(
        emptyList(), emptyList()
    ),
    attributes = mapOf(
        "original" to toJson()
    ),
    apiKeys = emptyList(),
    enabled = true,
    disabledBy = null,
    creationDate = System.currentTimeMillis(),
    disabledDate = null
)

fun InseeAddress.toAddress() = Address(
    street = street(),
    postalCode = codePostalEtablissement.orEmpty(),
    city = libelleCommuneEtablissement.orEmpty()
)

fun InseeAddress.street() = StringBuilder().apply {
    numeroVoieEtablissement?.let { append("$it ") }
    indiceRepetitionEtablissement?.let { append("$it ") }
    typeVoieEtablissement?.let { append("$it ") }
    libelleVoieEtablissement?.let { append(it) }
}.toString()
