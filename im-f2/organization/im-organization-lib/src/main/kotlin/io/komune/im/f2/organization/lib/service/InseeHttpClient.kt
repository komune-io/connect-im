package io.komune.im.f2.organization.lib.service

import io.komune.im.api.config.properties.InseeProperties
import io.komune.im.f2.organization.lib.model.insee.InseeResponse

class InseeHttpClient(
    private val inseeProperties: InseeProperties
): ClientJvm(
    baseUrl = inseeProperties.sireneApi,
    generateBearerToken = { inseeProperties.token }
) {
    suspend fun getOrganizationBySiret(siret: String): InseeResponse {
        return get("siret/$siret")
    }
}
