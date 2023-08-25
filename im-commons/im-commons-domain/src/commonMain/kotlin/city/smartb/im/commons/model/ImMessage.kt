package city.smartb.im.commons.model

import f2.dsl.cqrs.Command
import f2.dsl.cqrs.Message
import f2.dsl.cqrs.Query

interface ImMessage: Message {
    /**
     * Id of the realm to access. If null, use the same as the authentified user.
     * @example "im-test"
     * @default null
     */
    val realmId: String?
}

interface ImCommand: ImMessage, Command

interface ImQuery: ImMessage, Query
