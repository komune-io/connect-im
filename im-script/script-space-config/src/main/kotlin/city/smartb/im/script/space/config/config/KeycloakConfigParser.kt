package city.smartb.im.script.space.config.config

import city.smartb.im.commons.utils.ParserUtils



class KeycloakConfigParser {

    fun getConfiguration(configPath: String)
        = ParserUtils.getConfiguration(configPath, KeycloakConfigProperties::class.java)

}
