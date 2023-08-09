package i2.config.api.auth.config

import city.smartb.im.commons.utils.ParserUtils



class KeycloakConfigParser {

    fun getConfiguration(configPath: String)
        = ParserUtils.getConfiguration(configPath, KeycloakConfigProperties::class.java)

}
