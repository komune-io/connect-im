package city.smartb.im.f2.organization.lib.model

import city.smartb.im.commons.model.Address
import city.smartb.im.commons.utils.toJson
import city.smartb.im.f2.organization.domain.model.OrganizationDTOBase
import city.smartb.im.f2.organization.lib.model.insee.InseeAddress
import city.smartb.im.f2.organization.lib.model.insee.InseeOrganization

fun InseeOrganization.toOrganization() = OrganizationDTOBase(
    id = "",
    siret = siret,
    name = uniteLegale?.denominationUniteLegale.orEmpty(),
    description = null,
    address = adresseEtablissement?.toAddress(),
    website = null,
    roles = emptyList(),
    attributes = mapOf(
        "original" to toJson()
    ),
    logo = null,
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
