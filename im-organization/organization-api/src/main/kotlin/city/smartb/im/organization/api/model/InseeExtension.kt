package city.smartb.im.organization.api.model

import city.smartb.im.commons.model.AddressBase
import city.smartb.im.organization.api.model.insee.InseeAddress
import city.smartb.im.organization.api.model.insee.InseeOrganization
import city.smartb.im.organization.domain.model.Organization

fun InseeOrganization.toOrganization() = Organization(
    id = "",
    siret = siret,
    name = uniteLegale.denominationUniteLegale.orEmpty(),
    description = null,
    address = adresseEtablissement.toAddress(),
    website = null,
    roles = null,
    attributes = emptyMap(),
    creationDate = System.currentTimeMillis()
)

fun InseeAddress.toAddress() = AddressBase(
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
