package city.smartb.im.bdd.data

import city.smartb.im.apikey.domain.model.ApiKey
import city.smartb.im.organization.domain.model.Organization
import city.smartb.im.user.domain.model.User

class FetchContext {
//    lateinit var traces: List<TraceEntity>
    lateinit var organizations: List<Organization>
    lateinit var users: List<User>
    lateinit var apikeys: List<ApiKey>
}
