package city.smartb.im.organization.lib.service

import city.smartb.im.api.config.InseeConfig
import city.smartb.im.commons.http.ClientJvm
import city.smartb.im.organization.lib.model.insee.InseeResponse

class InseeHttpClient(
    private val inseeConfig: InseeConfig
): ClientJvm(
    baseUrl = inseeConfig.sireneApi,
    generateBearerToken = { inseeConfig.token }
) {
    suspend fun getOrganizationBySiret(siret: String): InseeResponse {
        return get("siret/$siret")
    }
}
