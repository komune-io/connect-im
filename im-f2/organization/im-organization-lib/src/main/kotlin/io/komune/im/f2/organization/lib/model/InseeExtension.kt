package io.komune.im.f2.organization.lib.model

import io.komune.im.commons.model.Address
import io.komune.im.commons.utils.toJson
import io.komune.im.f2.organization.domain.model.Organization
import io.komune.im.f2.organization.domain.model.OrganizationStatus
import io.komune.im.f2.organization.lib.model.insee.InseeAddress
import io.komune.im.f2.organization.lib.model.insee.InseeOrganization

fun InseeOrganization.toOrganization() = Organization(
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
    status = OrganizationStatus.PENDING.name,
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
