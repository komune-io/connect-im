/** A Kotlin value the export cannot express, such as an `Instant`: opaque to TypeScript. */
export declare interface KtOpaque<N extends string> {
    readonly __kotlinOpaque: N;
    readonly __doNotUseOrImplementIt: {
        readonly "kotlin.Opaque": unique symbol;
    };
}
/**
 * The JSON a Kotlin value of type `T` is sent as: a list or a set is an array, a map an object
 * (whose keys are optional when they are an enum's names), a `Long` a number, an enum its name,
 * an opaque value a string, and a nullable property may be absent. The exported types describe
 * the Kotlin objects; a body read from or written to the wire is a `Json<T>`, and the compiled
 * code accepts none: an instance of an exported class that implements an exported interface, or
 * of a data class, is refused at any depth.
 *
 * What it does not say: a sealed or polymorphic type carries no discriminant here, so a
 * serializer's `type` field is not typed. A nullable property may be absent: F2's Kotlin client
 * omits nulls (`explicitNulls = false`), Jackson and a default kotlinx `Json` write `null`. A
 * reader that requires the property refuses the JSON that omits it: a default kotlinx `Json`
 * does, Jackson and F2's `Json` read it as `null`. An instance of a class that is neither of the
 * two above is not told apart from a plain object, and a `Long` beyond 2^53 loses its precision
 * as a JavaScript number.
 */
export type Json<T> =
    T extends { asJsReadonlyArrayView(): ReadonlyArray<infer E> } ? Json<E>[]
    : T extends { asJsReadonlySetView(): ReadonlySet<infer E> } ? Json<E>[]
    : T extends { asJsReadonlyMapView(): ReadonlyMap<infer K, infer V> } ? JsonMap<JsonKey<K>, Json<V>>
    : T extends { readonly __kotlinOpaque: string } ? string
    : T extends bigint ? number
    : T extends string | number | boolean | null | undefined ? T
    : T extends { readonly name: infer N extends string; readonly ordinal: infer O extends number }
        ? string extends N ? JsonObject<T> : number extends O ? JsonObject<T> : N
    : T extends ReadonlyArray<infer E> ? Json<E>[]
    : T extends (...args: never[]) => unknown ? never
    : T extends object ? JsonObject<T>
    : T;
type JsonKey<K> = Json<K> extends string | number ? Json<K> : string;
// A map holds the entries it has: keys out of a finite set, such as an enum's names, are optional.
type JsonMap<K extends string | number, V> =
    string extends K ? Record<string, V> : number extends K ? Record<number, V> : { [P in K]?: V };
type JsonField<T, P extends keyof T> =
    P extends "__doNotUseOrImplementIt" ? never : T[P] extends (...args: never[]) => unknown ? never : P;
type JsonFields<T> = { [P in keyof T]-?: JsonField<T, P> }[keyof T];
type JsonOptional<T> = {
    [P in JsonFields<T>]-?: null extends T[P] ? P : undefined extends T[P] ? P : never
}[JsonFields<T>];
// What a Kotlin object has and JSON never does: the brand of an exported interface, the members of a data class.
type JsonObject<T> =
    { [P in Exclude<JsonFields<T>, JsonOptional<T>>]: Json<T[P]> } & { [P in JsonOptional<T>]?: Json<T[P]> }
    & { readonly [P in "__doNotUseOrImplementIt" | Exclude<"equals" | "hashCode", JsonFields<T>>]?: never };
type Nullable<T> = T | null | undefined
declare function KtSingleton<T>(): T & (abstract new() => object);
export declare interface KtList<out E> /* extends Collection<E> */ {
    asJsReadonlyArrayView(): ReadonlyArray<E>;
    readonly __doNotUseOrImplementIt: {
        readonly "kotlin.collections.KtList": unique symbol;
    };
}
export declare namespace KtList {
    function fromJsArray<E>(array: ReadonlyArray<E>): KtList<E>;
}
export declare interface KtMap<K, out V> {
    asJsReadonlyMapView(): ReadonlyMap<K, V>;
    readonly __doNotUseOrImplementIt: {
        readonly "kotlin.collections.KtMap": unique symbol;
    };
}
export declare namespace KtMap {
    function fromJsMap<K, V>(map: ReadonlyMap<K, V>): KtMap<K, V>;
}
export declare interface AuthedUserDTO {
    readonly id: string;
    readonly identifier: Nullable<string>;
    readonly memberOf: Nullable<string>;
    readonly roles: Nullable<Array<string>>;
    readonly acr: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.commons.auth.AuthedUserDTO": unique symbol;
    };
}
export declare abstract class ImPermission {
    private constructor();
    static get IM_FORCE_MFA_OTP(): ImPermission & {
        get name(): "IM_FORCE_MFA_OTP";
        get ordinal(): 0;
    };
    static get IM_USER_READ(): ImPermission & {
        get name(): "IM_USER_READ";
        get ordinal(): 1;
    };
    static get IM_USER_ROLE_WRITE(): ImPermission & {
        get name(): "IM_USER_ROLE_WRITE";
        get ordinal(): 2;
    };
    static get IM_USER_WRITE(): ImPermission & {
        get name(): "IM_USER_WRITE";
        get ordinal(): 3;
    };
    static get IM_MY_USER(): ImPermission & {
        get name(): "IM_MY_USER";
        get ordinal(): 4;
    };
    static get IM_ORGANIZATION_READ(): ImPermission & {
        get name(): "IM_ORGANIZATION_READ";
        get ordinal(): 5;
    };
    static get IM_ORGANIZATION_WRITE(): ImPermission & {
        get name(): "IM_ORGANIZATION_WRITE";
        get ordinal(): 6;
    };
    static get IM_MY_ORGANIZATION_WRITE(): ImPermission & {
        get name(): "IM_MY_ORGANIZATION_WRITE";
        get ordinal(): 7;
    };
    static get IM_MY_ORGANIZATION_READ(): ImPermission & {
        get name(): "IM_MY_ORGANIZATION_READ";
        get ordinal(): 8;
    };
    static get IM_ORGANIZATION_API_KEY_READ(): ImPermission & {
        get name(): "IM_ORGANIZATION_API_KEY_READ";
        get ordinal(): 9;
    };
    static get IM_ORGANIZATION_STATUS_WRITE(): ImPermission & {
        get name(): "IM_ORGANIZATION_STATUS_WRITE";
        get ordinal(): 10;
    };
    static get IM_APIKEY_READ(): ImPermission & {
        get name(): "IM_APIKEY_READ";
        get ordinal(): 11;
    };
    static get IM_APIKEY_WRITE(): ImPermission & {
        get name(): "IM_APIKEY_WRITE";
        get ordinal(): 12;
    };
    static get IM_SPACE_READ(): ImPermission & {
        get name(): "IM_SPACE_READ";
        get ordinal(): 13;
    };
    static get IM_SPACE_WRITE(): ImPermission & {
        get name(): "IM_SPACE_WRITE";
        get ordinal(): 14;
    };
    static get IM_ROLE_READ(): ImPermission & {
        get name(): "IM_ROLE_READ";
        get ordinal(): 15;
    };
    static get IM_ROLE_WRITE(): ImPermission & {
        get name(): "IM_ROLE_WRITE";
        get ordinal(): 16;
    };
    static values(): [typeof ImPermission.IM_FORCE_MFA_OTP, typeof ImPermission.IM_USER_READ, typeof ImPermission.IM_USER_ROLE_WRITE, typeof ImPermission.IM_USER_WRITE, typeof ImPermission.IM_MY_USER, typeof ImPermission.IM_ORGANIZATION_READ, typeof ImPermission.IM_ORGANIZATION_WRITE, typeof ImPermission.IM_MY_ORGANIZATION_WRITE, typeof ImPermission.IM_MY_ORGANIZATION_READ, typeof ImPermission.IM_ORGANIZATION_API_KEY_READ, typeof ImPermission.IM_ORGANIZATION_STATUS_WRITE, typeof ImPermission.IM_APIKEY_READ, typeof ImPermission.IM_APIKEY_WRITE, typeof ImPermission.IM_SPACE_READ, typeof ImPermission.IM_SPACE_WRITE, typeof ImPermission.IM_ROLE_READ, typeof ImPermission.IM_ROLE_WRITE];
    static valueOf(value: string): ImPermission;
    get name(): "IM_FORCE_MFA_OTP" | "IM_USER_READ" | "IM_USER_ROLE_WRITE" | "IM_USER_WRITE" | "IM_MY_USER" | "IM_ORGANIZATION_READ" | "IM_ORGANIZATION_WRITE" | "IM_MY_ORGANIZATION_WRITE" | "IM_MY_ORGANIZATION_READ" | "IM_ORGANIZATION_API_KEY_READ" | "IM_ORGANIZATION_STATUS_WRITE" | "IM_APIKEY_READ" | "IM_APIKEY_WRITE" | "IM_SPACE_READ" | "IM_SPACE_WRITE" | "IM_ROLE_READ" | "IM_ROLE_WRITE";
    get ordinal(): 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16;
    get identifier(): string;
}
export declare namespace ImPermission {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ImPermission;
    }
}
export declare abstract class ExceptionCodes {
    static readonly getInstance: () => typeof ExceptionCodes.$metadata$.type;
    private constructor();
}
export declare namespace ExceptionCodes {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            privilegeWrongTarget(): number;
            private constructor();
        }
    }
}
export declare interface AddressDTO {
    readonly street: string;
    readonly postalCode: string;
    readonly city: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.commons.model.AddressDTO": unique symbol;
    };
}
export declare class Address implements AddressDTO {
    constructor(street: string, postalCode: string, city: string);
    get street(): string;
    get postalCode(): string;
    get city(): string;
    copy(street?: string, postalCode?: string, city?: string): Address;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: AddressDTO["__doNotUseOrImplementIt"];
}
export declare namespace Address {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Address;
    }
}
export declare interface Command extends Message {
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.Command": unique symbol;
    } & Message["__doNotUseOrImplementIt"];
}
export declare interface Event extends Message {
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.Event": unique symbol;
    } & Message["__doNotUseOrImplementIt"];
}
export declare interface Message {
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.Message": unique symbol;
    };
}
export declare interface Problem extends Message {
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.Problem": unique symbol;
    } & Message["__doNotUseOrImplementIt"];
}
export declare interface Query extends Message {
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.Query": unique symbol;
    } & Message["__doNotUseOrImplementIt"];
}
export declare interface EnvelopeDTO<T> extends WithEnvelopeId, WithEnvelopeData<T>, Message {
    readonly id: string;
    readonly data: T;
    readonly type: string;
    readonly datacontenttype: Nullable<string>;
    readonly specversion: Nullable<string>;
    readonly source: Nullable<string>;
    readonly time: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.envelope.EnvelopeDTO": unique symbol;
    } & Message["__doNotUseOrImplementIt"] & WithEnvelopeData<unknown>["__doNotUseOrImplementIt"] & WithEnvelopeId["__doNotUseOrImplementIt"];
}
export declare class Envelope<T> implements EnvelopeDTO<T> {
    constructor(id: string, data: T, type: string, datacontenttype?: Nullable<string>, source?: Nullable<string>, time?: Nullable<string>);
    get id(): string;
    get data(): T;
    get type(): string;
    get datacontenttype(): Nullable<string>;
    get source(): Nullable<string>;
    get time(): Nullable<string>;
    get specversion(): string;
    copy(id?: string, data?: T, type?: string, datacontenttype?: Nullable<string>, source?: Nullable<string>, time?: Nullable<string>): Envelope<T>;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: EnvelopeDTO<unknown>["__doNotUseOrImplementIt"];
}
export declare namespace Envelope {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <T>() => Envelope<T>;
    }
}
export declare interface WithEnvelopeData<T> {
    readonly data: T;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.envelope.WithEnvelopeData": unique symbol;
    };
}
export declare interface WithEnvelopeId {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.envelope.WithEnvelopeId": unique symbol;
    };
}
export declare interface F2ErrorDTO extends Problem {
    readonly id: Nullable<string>;
    readonly timestamp: string;
    readonly code: number;
    readonly requestId: Nullable<string>;
    readonly message: string;
    readonly refusal: Nullable<F2RefusalDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.error.F2ErrorDTO": unique symbol;
    } & Problem["__doNotUseOrImplementIt"];
}
export declare interface F2RefusalDTO {
    readonly code: string;
    readonly parameters: KtMap<string, string>;
    readonly citers: KtList<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.error.F2RefusalDTO": unique symbol;
    };
}
export declare class F2Refusal implements F2RefusalDTO {
    constructor(code: string, parameters?: KtMap<string, string>, citers?: KtList<string>);
    get code(): string;
    get parameters(): KtMap<string, string>;
    get citers(): KtList<string>;
    copy(code?: string, parameters?: KtMap<string, string>, citers?: KtList<string>): F2Refusal;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: F2RefusalDTO["__doNotUseOrImplementIt"];
}
export declare namespace F2Refusal {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => F2Refusal;
    }
}
export declare class F2Error implements F2ErrorDTO {
    constructor(message: string, id?: Nullable<string>, timestamp?: string, code?: number, requestId?: Nullable<string>, refusal?: Nullable<F2Refusal>);
    get message(): string;
    get id(): Nullable<string>;
    get timestamp(): string;
    get code(): number;
    get requestId(): Nullable<string>;
    get refusal(): Nullable<F2Refusal>;
    toString(): string;
    readonly __doNotUseOrImplementIt: F2ErrorDTO["__doNotUseOrImplementIt"];
}
export declare namespace F2Error {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => F2Error;
    }
}
export declare interface Match<T> {
    readonly isNegative: boolean;
    map<R>(transform: (value: T) => R): Match<R>;
    not(): Match<T>;
    and(match: Match<T>): Match<T>;
    or(match: Match<T>): Match<T>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.filter.Match": unique symbol;
    };
}
export declare interface SortDTO {
    readonly property: string;
    readonly ascending: boolean;
    readonly nullsFirst: Nullable<boolean>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.filter.SortDTO": unique symbol;
    };
}
export declare interface PageDTO<out OBJECT> {
    readonly total: number;
    readonly items: KtList<OBJECT>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.page.PageDTO": unique symbol;
    };
}
export declare class Page<out OBJECT> implements PageDTO<OBJECT> {
    constructor(total: number, items: KtList<OBJECT>);
    get total(): number;
    get items(): KtList<OBJECT>;
    copy(total?: number, items?: KtList<OBJECT>): Page<OBJECT>;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PageDTO<unknown>["__doNotUseOrImplementIt"];
}
export declare namespace Page {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <OBJECT>() => Page<OBJECT>;
    }
}
export declare interface PageQueryDTO extends Query {
    readonly pagination: Nullable<OffsetPaginationDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.page.PageQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare interface PageQueryResultDTO<out OBJECT> extends Event, PageDTO<OBJECT> {
    readonly total: number;
    readonly items: KtList<OBJECT>;
    readonly pagination: Nullable<OffsetPaginationDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.page.PageQueryResultDTO": unique symbol;
    } & PageDTO<unknown>["__doNotUseOrImplementIt"] & Event["__doNotUseOrImplementIt"];
}
export declare class PageQuery implements PageQueryDTO {
    constructor(pagination: Nullable<OffsetPaginationDTO>);
    get pagination(): Nullable<OffsetPaginationDTO>;
    copy(pagination?: Nullable<OffsetPaginationDTO>): PageQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PageQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace PageQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PageQuery;
    }
}
export declare class PageQueryResult<out OBJECT> implements PageQueryResultDTO<OBJECT> {
    constructor(pagination: Nullable<OffsetPagination>, total: number, items: KtList<OBJECT>);
    get pagination(): Nullable<OffsetPagination>;
    get total(): number;
    get items(): KtList<OBJECT>;
    copy(pagination?: Nullable<OffsetPagination>, total?: number, items?: KtList<OBJECT>): PageQueryResult<OBJECT>;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PageQueryResultDTO<unknown>["__doNotUseOrImplementIt"];
}
export declare namespace PageQueryResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <OBJECT>() => PageQueryResult<OBJECT>;
    }
}
export declare interface Pagination {
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.page.Pagination": unique symbol;
    };
}
export declare interface OffsetPaginationDTO extends Pagination {
    readonly offset: number;
    readonly limit: number;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.page.OffsetPaginationDTO": unique symbol;
    } & Pagination["__doNotUseOrImplementIt"];
}
export declare interface PagePaginationDTO extends Pagination {
    readonly page: Nullable<number>;
    readonly size: Nullable<number>;
    readonly __doNotUseOrImplementIt: {
        readonly "f2.dsl.cqrs.page.PagePaginationDTO": unique symbol;
    } & Pagination["__doNotUseOrImplementIt"];
}
export declare class OffsetPagination implements OffsetPaginationDTO {
    constructor(offset: number, limit: number);
    get offset(): number;
    get limit(): number;
    copy(offset?: number, limit?: number): OffsetPagination;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OffsetPaginationDTO["__doNotUseOrImplementIt"];
}
export declare namespace OffsetPagination {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OffsetPagination;
    }
}
export declare class PagePagination implements PagePaginationDTO {
    constructor(page: Nullable<number>, size: Nullable<number>);
    get page(): Nullable<number>;
    get size(): Nullable<number>;
    copy(page?: Nullable<number>, size?: Nullable<number>): PagePagination;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PagePaginationDTO["__doNotUseOrImplementIt"];
}
export declare namespace PagePagination {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PagePagination;
    }
}
export declare interface SpaceDefineCommandDTO extends Command {
    readonly identifier: string;
    readonly displayName: Nullable<string>;
    readonly theme: Nullable<string>;
    readonly smtp: Nullable<KtMap<string, string>>;
    readonly locales: Nullable<KtList<string>>;
    readonly settings: Nullable<SpaceSettingsDTO>;
    readonly mfa: Nullable<KtList<string>>;
    readonly sslRequired: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.command.SpaceDefineCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare class SpaceDefineCommand implements SpaceDefineCommandDTO {
    constructor(identifier: string, displayName: Nullable<string>, theme: Nullable<string>, smtp: Nullable<KtMap<string, string>>, locales: Nullable<KtList<string>>, settings: Nullable<SpaceSettings>, mfa: Nullable<KtList<string>>, sslRequired?: Nullable<string>);
    get identifier(): string;
    get displayName(): Nullable<string>;
    get theme(): Nullable<string>;
    get smtp(): Nullable<KtMap<string, string>>;
    get locales(): Nullable<KtList<string>>;
    get settings(): Nullable<SpaceSettings>;
    get mfa(): Nullable<KtList<string>>;
    get sslRequired(): Nullable<string>;
    copy(identifier?: string, displayName?: Nullable<string>, theme?: Nullable<string>, smtp?: Nullable<KtMap<string, string>>, locales?: Nullable<KtList<string>>, settings?: Nullable<SpaceSettings>, mfa?: Nullable<KtList<string>>, sslRequired?: Nullable<string>): SpaceDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceDefineCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceDefineCommand;
    }
}
export declare interface SpaceSettingsDTO {
    readonly registrationAllowed: Nullable<boolean>;
    readonly rememberMe: Nullable<boolean>;
    readonly resetPasswordAllowed: Nullable<boolean>;
    readonly registrationEmailAsUsername: Nullable<boolean>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.command.SpaceSettingsDTO": unique symbol;
    };
}
export declare class SpaceSettings implements SpaceSettingsDTO {
    constructor(registrationAllowed: Nullable<boolean>, rememberMe: Nullable<boolean>, resetPasswordAllowed: Nullable<boolean>, registrationEmailAsUsername: Nullable<boolean>);
    get registrationAllowed(): Nullable<boolean>;
    get rememberMe(): Nullable<boolean>;
    get resetPasswordAllowed(): Nullable<boolean>;
    get registrationEmailAsUsername(): Nullable<boolean>;
    copy(registrationAllowed?: Nullable<boolean>, rememberMe?: Nullable<boolean>, resetPasswordAllowed?: Nullable<boolean>, registrationEmailAsUsername?: Nullable<boolean>): SpaceSettings;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceSettingsDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceSettings {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceSettings;
    }
}
export declare interface SpaceDefinedEventDTO extends Event {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.command.SpaceDefinedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class SpaceDefinedEvent implements SpaceDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): SpaceDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceDefinedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceDefinedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceDefinedEvent;
    }
}
export declare interface SpaceDeleteCommandDTO extends Command {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.command.SpaceDeleteCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare class SpaceDeleteCommand implements SpaceDeleteCommandDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): SpaceDeleteCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceDeleteCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceDeleteCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceDeleteCommand;
    }
}
export declare interface SpaceDeletedEventDTO extends Event {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.command.SpaceDeletedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class SpaceDeletedEvent implements SpaceDeletedEventDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): SpaceDeletedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceDeletedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceDeletedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceDeletedEvent;
    }
}
export declare interface SpaceDTO {
    readonly identifier: Nullable<string>;
    readonly displayName: Nullable<string>;
    readonly smtp: Nullable<KtMap<string, string>>;
    readonly theme: Nullable<string>;
    readonly locales: Nullable<KtList<string>>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.model.SpaceDTO": unique symbol;
    };
}
export declare class Space implements SpaceDTO {
    constructor(identifier: string, displayName: Nullable<string>, theme: Nullable<string>, smtp: Nullable<KtMap<string, string>>, locales: Nullable<KtList<string>>);
    get identifier(): string;
    get displayName(): Nullable<string>;
    get theme(): Nullable<string>;
    get smtp(): Nullable<KtMap<string, string>>;
    get locales(): Nullable<KtList<string>>;
    copy(identifier?: string, displayName?: Nullable<string>, theme?: Nullable<string>, smtp?: Nullable<KtMap<string, string>>, locales?: Nullable<KtList<string>>): Space;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceDTO["__doNotUseOrImplementIt"];
}
export declare namespace Space {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Space;
    }
}
export declare abstract class SpacePolicies {
    static readonly getInstance: () => typeof SpacePolicies.$metadata$.type;
    private constructor();
}
export declare namespace SpacePolicies {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            canGet(authedUser: AuthedUserDTO): boolean;
            canPage(authedUser: AuthedUserDTO): boolean;
            canDefine(authedUser: AuthedUserDTO): boolean;
            canDelete(authedUser: AuthedUserDTO): boolean;
            private constructor();
        }
    }
}
export declare interface SpaceGetQueryDTO extends Query {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.query.SpaceGetQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class SpaceGetQuery implements SpaceGetQueryDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): SpaceGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceGetQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceGetQuery;
    }
}
export declare interface SpaceGetResultDTO extends Event {
    readonly item: Nullable<SpaceDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.query.SpaceGetResultDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class SpaceGetResult implements SpaceGetResultDTO {
    constructor(item: Nullable<Space>);
    get item(): Nullable<Space>;
    copy(item?: Nullable<Space>): SpaceGetResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpaceGetResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpaceGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpaceGetResult;
    }
}
export declare interface SpacePageQueryDTO extends Query {
    readonly search: Nullable<string>;
    readonly page: Nullable<number>;
    readonly size: Nullable<number>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.query.SpacePageQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class SpacePageQuery implements SpacePageQueryDTO {
    constructor(search?: Nullable<string>, page?: Nullable<number>, size?: Nullable<number>);
    get search(): Nullable<string>;
    get page(): Nullable<number>;
    get size(): Nullable<number>;
    copy(search?: Nullable<string>, page?: Nullable<number>, size?: Nullable<number>): SpacePageQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpacePageQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpacePageQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpacePageQuery;
    }
}
export declare interface SpacePageResultDTO extends PageDTO<SpaceDTO> {
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.space.domain.query.SpacePageResultDTO": unique symbol;
    } & PageDTO<unknown>["__doNotUseOrImplementIt"];
}
export declare class SpacePageResult implements SpacePageResultDTO {
    constructor(items: KtList<Space>, pagination: Nullable<PagePagination>, total: number);
    get items(): KtList<Space>;
    get pagination(): Nullable<PagePagination>;
    get total(): number;
    copy(items?: KtList<Space>, pagination?: Nullable<PagePagination>, total?: number): SpacePageResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: SpacePageResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace SpacePageResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => SpacePageResult;
    }
}