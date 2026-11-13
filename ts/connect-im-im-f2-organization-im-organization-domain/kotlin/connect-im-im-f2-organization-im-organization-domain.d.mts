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
 *
 * These types require `strictNullChecks`: without it, `null` extends every property's type, so
 * every property of a `Json<T>` is optional.
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
export declare interface OrganizationCoreDeleteCommandDTO extends Command {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.core.organization.domain.command.OrganizationCoreDeleteCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare class OrganizationCoreDeleteCommand implements OrganizationCoreDeleteCommandDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): OrganizationCoreDeleteCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationCoreDeleteCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationCoreDeleteCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationCoreDeleteCommand;
    }
}
export declare interface OrganizationCoreDeletedEventDTO extends Event {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.core.organization.domain.command.OrganizationCoreDeletedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationCoreDeletedEvent implements OrganizationCoreDeletedEventDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): OrganizationCoreDeletedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationCoreDeletedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationCoreDeletedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationCoreDeletedEvent;
    }
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
export declare abstract class PrivilegePolicies {
    static readonly getInstance: () => typeof PrivilegePolicies.$metadata$.type;
    private constructor();
}
export declare namespace PrivilegePolicies {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            canGet(authedUser: AuthedUserDTO): boolean;
            canList(authedUser: AuthedUserDTO): boolean;
            canDefine(authedUser: AuthedUserDTO): boolean;
            private constructor();
        }
    }
}
export declare interface FeatureDefineCommandDTO {
    readonly identifier: string;
    readonly description: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.command.FeatureDefineCommandDTO": unique symbol;
    };
}
export declare class FeatureDefineCommand implements FeatureDefineCommandDTO {
    constructor(identifier: string, description: string);
    get identifier(): string;
    get description(): string;
    copy(identifier?: string, description?: string): FeatureDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: FeatureDefineCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace FeatureDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureDefineCommand;
    }
}
export declare interface FeatureDefinedEventDTO extends Event {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.command.FeatureDefinedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class FeatureDefinedEvent implements FeatureDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): FeatureDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: FeatureDefinedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace FeatureDefinedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureDefinedEvent;
    }
}
export declare interface FeatureDTO extends PrivilegeDTO {
    readonly id: string;
    readonly type: string;
    readonly identifier: string;
    readonly description: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.model.FeatureDTO": unique symbol;
    } & PrivilegeDTO["__doNotUseOrImplementIt"];
}
export declare class Feature implements FeatureDTO {
    constructor(id: string, identifier: string, description: string, type?: string);
    get id(): string;
    get identifier(): string;
    get description(): string;
    get type(): string;
    copy(id?: string, identifier?: string, description?: string, type?: string): Feature;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: FeatureDTO["__doNotUseOrImplementIt"];
}
export declare namespace Feature {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Feature;
    }
}
export declare interface FeatureGetQueryDTO {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.query.FeatureGetQueryDTO": unique symbol;
    };
}
export declare class FeatureGetQuery implements FeatureGetQueryDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): FeatureGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: FeatureGetQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace FeatureGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureGetQuery;
    }
}
export declare interface FeatureGetResultDTO {
    readonly item: Nullable<FeatureDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.query.FeatureGetResultDTO": unique symbol;
    };
}
export declare class FeatureGetResult implements FeatureGetResultDTO {
    constructor(item: Nullable<Feature>);
    get item(): Nullable<Feature>;
    copy(item?: Nullable<Feature>): FeatureGetResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: FeatureGetResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace FeatureGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureGetResult;
    }
}
export declare interface FeatureListQueryDTO {
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.query.FeatureListQueryDTO": unique symbol;
    };
}
export declare class FeatureListQuery implements FeatureListQueryDTO {
    constructor();
    readonly __doNotUseOrImplementIt: FeatureListQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace FeatureListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureListQuery;
    }
}
export declare interface FeatureListResultDTO {
    readonly items: KtList<FeatureDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.feature.query.FeatureListResultDTO": unique symbol;
    };
}
export declare class FeatureListResult implements FeatureListResultDTO {
    constructor(items: KtList<Feature>);
    get items(): KtList<Feature>;
    copy(items?: KtList<Feature>): FeatureListResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: FeatureListResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace FeatureListResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureListResult;
    }
}
export declare interface PrivilegeDTO {
    readonly id: string;
    readonly identifier: string;
    readonly description: string;
    readonly type: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.model.PrivilegeDTO": unique symbol;
    };
}
export declare interface PermissionDefineCommandDTO {
    readonly identifier: string;
    readonly description: string;
    readonly features: Nullable<KtList<KtList<string>>>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.command.PermissionDefineCommandDTO": unique symbol;
    };
}
export declare class PermissionDefineCommand implements PermissionDefineCommandDTO {
    constructor(identifier: string, description: string, features: Nullable<KtList<KtList<string>>>);
    get identifier(): string;
    get description(): string;
    get features(): Nullable<KtList<KtList<string>>>;
    copy(identifier?: string, description?: string, features?: Nullable<KtList<KtList<string>>>): PermissionDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PermissionDefineCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace PermissionDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionDefineCommand;
    }
}
export declare interface PermissionDefinedEventDTO extends Event {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.command.PermissionDefinedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class PermissionDefinedEvent implements PermissionDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): PermissionDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PermissionDefinedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace PermissionDefinedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionDefinedEvent;
    }
}
export declare interface PermissionDTO extends PrivilegeDTO {
    readonly id: string;
    readonly type: string;
    readonly identifier: string;
    readonly description: string;
    readonly features: Nullable<KtList<KtList<string>>>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.model.PermissionDTO": unique symbol;
    } & PrivilegeDTO["__doNotUseOrImplementIt"];
}
export declare class Permission implements PermissionDTO {
    constructor(id: string, identifier: string, description: string, features: Nullable<KtList<KtList<string>>>, type?: string);
    get id(): string;
    get identifier(): string;
    get description(): string;
    get features(): Nullable<KtList<KtList<string>>>;
    get type(): string;
    copy(id?: string, identifier?: string, description?: string, features?: Nullable<KtList<KtList<string>>>, type?: string): Permission;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PermissionDTO["__doNotUseOrImplementIt"];
}
export declare namespace Permission {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Permission;
    }
}
export declare interface PermissionGetQueryDTO {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.query.PermissionGetQueryDTO": unique symbol;
    };
}
export declare class PermissionGetQuery implements PermissionGetQueryDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): PermissionGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PermissionGetQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace PermissionGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionGetQuery;
    }
}
export declare interface PermissionGetResultDTO {
    readonly item: Nullable<PermissionDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.query.PermissionGetResultDTO": unique symbol;
    };
}
export declare class PermissionGetResult implements PermissionGetResultDTO {
    constructor(item: Nullable<Permission>);
    get item(): Nullable<Permission>;
    copy(item?: Nullable<Permission>): PermissionGetResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PermissionGetResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace PermissionGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionGetResult;
    }
}
export declare interface PermissionListQueryDTO {
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.query.PermissionListQueryDTO": unique symbol;
    };
}
export declare class PermissionListQuery implements PermissionListQueryDTO {
    constructor();
    readonly __doNotUseOrImplementIt: PermissionListQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace PermissionListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionListQuery;
    }
}
export declare interface PermissionListResultDTO {
    readonly items: KtList<PermissionDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.permission.query.PermissionListResultDTO": unique symbol;
    };
}
export declare class PermissionListResult implements PermissionListResultDTO {
    constructor(items: KtList<Permission>);
    get items(): KtList<Permission>;
    copy(items?: KtList<Permission>): PermissionListResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: PermissionListResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace PermissionListResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionListResult;
    }
}
export declare interface RoleDefineCommandDTO {
    readonly identifier: string;
    readonly description: string;
    readonly targets: KtList<string>;
    readonly locale: KtMap<string, string>;
    readonly bindings: Nullable<KtMap<string, KtList<string>>>;
    readonly permissions: Nullable<KtList<string>>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.command.RoleDefineCommandDTO": unique symbol;
    };
}
export declare class RoleDefineCommand implements RoleDefineCommandDTO {
    constructor(identifier: string, description: string, targets: KtList<string>, locale: KtMap<string, string>, bindings: Nullable<KtMap<string, KtList<string>>>, permissions: Nullable<KtList<string>>);
    get identifier(): string;
    get description(): string;
    get targets(): KtList<string>;
    get locale(): KtMap<string, string>;
    get bindings(): Nullable<KtMap<string, KtList<string>>>;
    get permissions(): Nullable<KtList<string>>;
    copy(identifier?: string, description?: string, targets?: KtList<string>, locale?: KtMap<string, string>, bindings?: Nullable<KtMap<string, KtList<string>>>, permissions?: Nullable<KtList<string>>): RoleDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleDefineCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace RoleDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleDefineCommand;
    }
}
export declare interface RoleDefinedEventDTO extends Event {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.command.RoleDefinedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class RoleDefinedEvent implements RoleDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): RoleDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleDefinedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace RoleDefinedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleDefinedEvent;
    }
}
export declare interface RoleDTO extends PrivilegeDTO {
    readonly id: string;
    readonly type: string;
    readonly identifier: string;
    readonly description: string;
    readonly targets: KtList<string>;
    readonly locale: KtMap<string, string>;
    readonly bindings: KtMap<string, KtList<RoleDTO>>;
    readonly permissions: KtList<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.model.RoleDTO": unique symbol;
    } & PrivilegeDTO["__doNotUseOrImplementIt"];
}
export declare class Role implements RoleDTO {
    constructor(id: string, identifier: string, description: string, targets: KtList<string>, locale: KtMap<string, string>, bindings: KtMap<string, KtList<Role>>, permissions: KtList<string>, type?: string);
    get id(): string;
    get identifier(): string;
    get description(): string;
    get targets(): KtList<string>;
    get locale(): KtMap<string, string>;
    get bindings(): KtMap<string, KtList<Role>>;
    get permissions(): KtList<string>;
    get type(): string;
    copy(id?: string, identifier?: string, description?: string, targets?: KtList<string>, locale?: KtMap<string, string>, bindings?: KtMap<string, KtList<Role>>, permissions?: KtList<string>, type?: string): Role;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleDTO["__doNotUseOrImplementIt"];
}
export declare namespace Role {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Role;
    }
}
export declare abstract class RoleTargetValues {
    static readonly getInstance: () => typeof RoleTargetValues.$metadata$.type;
    private constructor();
}
export declare namespace RoleTargetValues {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            get ORGANIZATION(): string;
            get USER(): string;
            get API_KEY(): string;
            private constructor();
        }
    }
}
export declare interface RoleGetQueryDTO {
    readonly identifier: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.query.RoleGetQueryDTO": unique symbol;
    };
}
export declare class RoleGetQuery implements RoleGetQueryDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): RoleGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleGetQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace RoleGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleGetQuery;
    }
}
export declare interface RoleGetResultDTO {
    readonly item: Nullable<RoleDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.query.RoleGetResultDTO": unique symbol;
    };
}
export declare class RoleGetResult implements RoleGetResultDTO {
    constructor(item: Nullable<Role>);
    get item(): Nullable<Role>;
    copy(item?: Nullable<Role>): RoleGetResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleGetResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace RoleGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleGetResult;
    }
}
export declare interface RoleListQueryDTO {
    readonly target: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.query.RoleListQueryDTO": unique symbol;
    };
}
export declare class RoleListQuery implements RoleListQueryDTO {
    constructor(target: Nullable<string>);
    get target(): Nullable<string>;
    copy(target?: Nullable<string>): RoleListQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleListQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace RoleListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleListQuery;
    }
}
export declare interface RoleListResultDTO {
    readonly items: KtList<RoleDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.privilege.domain.role.query.RoleListResultDTO": unique symbol;
    };
}
export declare class RoleListResult implements RoleListResultDTO {
    constructor(items: KtList<Role>);
    get items(): KtList<Role>;
    copy(items?: KtList<Role>): RoleListResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: RoleListResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace RoleListResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleListResult;
    }
}
export declare interface OrganizationCreateCommandDTO extends Command {
    readonly siret: Nullable<string>;
    readonly name: string;
    readonly description: Nullable<string>;
    readonly address: Nullable<AddressDTO>;
    readonly website: Nullable<string>;
    readonly roles: Nullable<KtList<string>>;
    readonly parentOrganizationId: Nullable<string>;
    readonly attributes: Nullable<KtMap<string, string>>;
    readonly status: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationCreateCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare class OrganizationCreateCommand implements OrganizationCreateCommandDTO {
    constructor(siret: Nullable<string> | undefined, name: string, description?: Nullable<string>, address?: Nullable<Address>, website?: Nullable<string>, roles?: Nullable<KtList<string>>, parentOrganizationId?: Nullable<string>, attributes?: Nullable<KtMap<string, string>>, status?: string);
    get siret(): Nullable<string>;
    get name(): string;
    get description(): Nullable<string>;
    get address(): Nullable<Address>;
    get website(): Nullable<string>;
    get roles(): Nullable<KtList<string>>;
    get parentOrganizationId(): Nullable<string>;
    get attributes(): Nullable<KtMap<string, string>>;
    get status(): string;
    copy(siret?: Nullable<string>, name?: string, description?: Nullable<string>, address?: Nullable<Address>, website?: Nullable<string>, roles?: Nullable<KtList<string>>, parentOrganizationId?: Nullable<string>, attributes?: Nullable<KtMap<string, string>>, status?: string): OrganizationCreateCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationCreateCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationCreateCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationCreateCommand;
    }
}
export declare interface OrganizationCreatedEventDTO extends Event {
    readonly id: string;
    readonly parentOrganization: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationCreatedEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationCreatedEvent implements OrganizationCreatedEventDTO {
    constructor(id: string, parentOrganization?: Nullable<string>);
    get id(): string;
    get parentOrganization(): Nullable<string>;
    copy(id?: string, parentOrganization?: Nullable<string>): OrganizationCreatedEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationCreatedEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationCreatedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationCreatedEvent;
    }
}
export declare interface OrganizationDeleteCommandDTO extends OrganizationCoreDeleteCommandDTO {
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationDeleteCommandDTO": unique symbol;
    } & OrganizationCoreDeleteCommandDTO["__doNotUseOrImplementIt"];
}
export declare interface OrganizationDeletedEventDTO extends OrganizationCoreDeletedEventDTO {
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationDeletedEventDTO": unique symbol;
    } & OrganizationCoreDeletedEventDTO["__doNotUseOrImplementIt"];
}
export declare interface OrganizationDisableCommandDTO extends Command {
    readonly id: string;
    readonly disabledBy: Nullable<string>;
    readonly anonymize: boolean;
    readonly attributes: Nullable<KtMap<string, string>>;
    readonly userAttributes: Nullable<KtMap<string, string>>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationDisableCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare class OrganizationDisableCommand implements OrganizationDisableCommandDTO {
    constructor(id: string, disabledBy: Nullable<string>, anonymize: boolean, attributes: Nullable<KtMap<string, string>>, userAttributes: Nullable<KtMap<string, string>>);
    get id(): string;
    get disabledBy(): Nullable<string>;
    get anonymize(): boolean;
    get attributes(): Nullable<KtMap<string, string>>;
    get userAttributes(): Nullable<KtMap<string, string>>;
    copy(id?: string, disabledBy?: Nullable<string>, anonymize?: boolean, attributes?: Nullable<KtMap<string, string>>, userAttributes?: Nullable<KtMap<string, string>>): OrganizationDisableCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationDisableCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationDisableCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationDisableCommand;
    }
}
export declare interface OrganizationDisabledEventDTO extends Event {
    readonly id: string;
    readonly userIds: KtList<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationDisabledEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationDisabledEvent implements OrganizationDisabledEventDTO {
    constructor(id: string, userIds: KtList<string>);
    get id(): string;
    get userIds(): KtList<string>;
    copy(id?: string, userIds?: KtList<string>): OrganizationDisabledEvent;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationDisabledEventDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationDisabledEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationDisabledEvent;
    }
}
export declare interface OrganizationUpdateCommandDTO extends Command {
    readonly id: string;
    readonly name: string;
    readonly description: Nullable<string>;
    readonly address: Nullable<AddressDTO>;
    readonly website: Nullable<string>;
    readonly roles: Nullable<KtList<string>>;
    readonly attributes: Nullable<KtMap<string, string>>;
    readonly status: Nullable<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationUpdateCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare class OrganizationUpdateCommand implements OrganizationUpdateCommandDTO {
    constructor(id: string, name: string, description: Nullable<string>, address: Nullable<Address>, website: Nullable<string>, roles: Nullable<KtList<string>>, attributes: Nullable<KtMap<string, string>>, status: Nullable<string>);
    get id(): string;
    get name(): string;
    get description(): Nullable<string>;
    get address(): Nullable<Address>;
    get website(): Nullable<string>;
    get roles(): Nullable<KtList<string>>;
    get attributes(): Nullable<KtMap<string, string>>;
    get status(): Nullable<string>;
    copy(id?: string, name?: string, description?: Nullable<string>, address?: Nullable<Address>, website?: Nullable<string>, roles?: Nullable<KtList<string>>, attributes?: Nullable<KtMap<string, string>>, status?: Nullable<string>): OrganizationUpdateCommand;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationUpdateCommandDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationUpdateCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationUpdateCommand;
    }
}
export declare interface OrganizationUpdatedResultDTO extends Event {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationUpdatedResultDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationUpdatedResult implements OrganizationUpdatedResultDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): OrganizationUpdatedResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationUpdatedResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationUpdatedResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationUpdatedResult;
    }
}
export declare interface OrganizationUploadLogoCommandDTO extends Command {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationUploadLogoCommandDTO": unique symbol;
    } & Command["__doNotUseOrImplementIt"];
}
export declare interface OrganizationUploadedLogoEventDTO extends Event {
    readonly id: string;
    readonly url: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.command.OrganizationUploadedLogoEventDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare interface OrganizationDTO {
    readonly id: string;
    readonly siret: Nullable<string>;
    readonly name: string;
    readonly description: Nullable<string>;
    readonly address: Nullable<AddressDTO>;
    readonly website: Nullable<string>;
    readonly attributes: KtMap<string, string>;
    readonly roles: KtList<RoleDTO>;
    readonly logo: Nullable<string>;
    readonly enabled: boolean;
    readonly status: string;
    readonly disabledBy: Nullable<string>;
    readonly creationDate: bigint;
    readonly disabledDate: Nullable<bigint>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.model.OrganizationDTO": unique symbol;
    };
}
export declare class Organization implements OrganizationDTO {
    constructor(id: string, siret: Nullable<string>, name: string, description: Nullable<string>, address: Nullable<Address>, website: Nullable<string>, attributes: KtMap<string, string>, roles: KtList<Role>, logo: Nullable<string>, status: string, enabled: boolean, disabledBy: Nullable<string>, creationDate: bigint, disabledDate: Nullable<bigint>);
    get id(): string;
    get siret(): Nullable<string>;
    get name(): string;
    get description(): Nullable<string>;
    get address(): Nullable<Address>;
    get website(): Nullable<string>;
    get attributes(): KtMap<string, string>;
    get roles(): KtList<Role>;
    get logo(): Nullable<string>;
    get status(): string;
    get enabled(): boolean;
    get disabledBy(): Nullable<string>;
    get creationDate(): bigint;
    get disabledDate(): Nullable<bigint>;
    copy(id?: string, siret?: Nullable<string>, name?: string, description?: Nullable<string>, address?: Nullable<Address>, website?: Nullable<string>, attributes?: KtMap<string, string>, roles?: KtList<Role>, logo?: Nullable<string>, status?: string, enabled?: boolean, disabledBy?: Nullable<string>, creationDate?: bigint, disabledDate?: Nullable<bigint>): Organization;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationDTO["__doNotUseOrImplementIt"];
}
export declare namespace Organization {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Organization;
    }
}
export declare interface OrganizationRefDTO {
    readonly id: string;
    readonly name: string;
    readonly roles: KtList<string>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.model.OrganizationRefDTO": unique symbol;
    };
}
export declare class OrganizationRef implements OrganizationRefDTO {
    constructor(id: string, name: string, roles: KtList<string>);
    get id(): string;
    get name(): string;
    get roles(): KtList<string>;
    copy(id?: string, name?: string, roles?: KtList<string>): OrganizationRef;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationRefDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationRef {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationRef;
    }
}
export declare abstract class OrganizationStatusValues {
    static readonly getInstance: () => typeof OrganizationStatusValues.$metadata$.type;
    private constructor();
}
export declare namespace OrganizationStatusValues {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            pending(): string;
            validated(): string;
            rejected(): string;
            private constructor();
        }
    }
}
export declare abstract class OrganizationPolicies {
    static readonly getInstance: () => typeof OrganizationPolicies.$metadata$.type;
    private constructor();
}
export declare namespace OrganizationPolicies {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            canGet(authedUser: AuthedUserDTO, organizationId: string): boolean;
            canList(authedUser: AuthedUserDTO): boolean;
            checkRefList(authedUser: Nullable<AuthedUserDTO>): boolean;
            canCreate(authedUser: AuthedUserDTO): boolean;
            canUpdate(authedUser: AuthedUserDTO, organizationId: string): boolean;
            canUpdateStatus(authedUser: AuthedUserDTO): boolean;
            canDisable(authedUser: AuthedUserDTO): boolean;
            canDelete(authedUser: AuthedUserDTO): boolean;
            private constructor();
        }
    }
}
export declare interface OrganizationGetFromInseeQueryDTO extends Query {
    readonly siret: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class OrganizationGetFromInseeQuery implements OrganizationGetFromInseeQueryDTO {
    constructor(siret: string);
    get siret(): string;
    copy(siret?: string): OrganizationGetFromInseeQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationGetFromInseeQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationGetFromInseeQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationGetFromInseeQuery;
    }
}
export declare interface OrganizationGetFromInseeResultDTO extends Event {
    readonly item: Nullable<OrganizationDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationGetFromInseeResultDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationGetFromInseeResult implements OrganizationGetFromInseeResultDTO {
    constructor(item: Nullable<Organization>);
    get item(): Nullable<Organization>;
    copy(item?: Nullable<Organization>): OrganizationGetFromInseeResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationGetFromInseeResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationGetFromInseeResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationGetFromInseeResult;
    }
}
export declare interface OrganizationGetQueryDTO extends Query {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationGetQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class OrganizationGetQuery implements OrganizationGetQueryDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): OrganizationGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationGetQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationGetQuery;
    }
}
export declare interface OrganizationGetResultDTO extends Event {
    readonly item: Nullable<OrganizationDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationGetResultDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationGetResult implements OrganizationGetResultDTO {
    constructor(item: Nullable<Organization>);
    get item(): Nullable<Organization>;
    copy(item?: Nullable<Organization>): OrganizationGetResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationGetResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationGetResult;
    }
}
export declare interface OrganizationPageQueryDTO extends Query {
    readonly name: Nullable<string>;
    readonly role: Nullable<string>;
    readonly roles: Nullable<KtList<string>>;
    readonly attributes: Nullable<KtMap<string, string>>;
    readonly status: Nullable<KtList<string>>;
    readonly withDisabled: Nullable<boolean>;
    readonly offset: Nullable<number>;
    readonly limit: Nullable<number>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationPageQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class OrganizationPageQuery implements OrganizationPageQueryDTO {
    constructor(name: Nullable<string> | undefined, role: Nullable<string> | undefined, attributes: Nullable<KtMap<string, string>> | undefined, status: Nullable<KtList<string>> | undefined, withDisabled: Nullable<boolean> | undefined, offset: Nullable<number>, limit: Nullable<number>, roles?: Nullable<KtList<string>>);
    get name(): Nullable<string>;
    get role(): Nullable<string>;
    get attributes(): Nullable<KtMap<string, string>>;
    get status(): Nullable<KtList<string>>;
    get withDisabled(): Nullable<boolean>;
    get offset(): Nullable<number>;
    get limit(): Nullable<number>;
    get roles(): Nullable<KtList<string>>;
    copy(name?: Nullable<string>, role?: Nullable<string>, attributes?: Nullable<KtMap<string, string>>, status?: Nullable<KtList<string>>, withDisabled?: Nullable<boolean>, offset?: Nullable<number>, limit?: Nullable<number>, roles?: Nullable<KtList<string>>): OrganizationPageQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationPageQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationPageQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationPageQuery;
    }
}
export declare interface OrganizationPageResultDTO extends PageDTO<OrganizationDTO> {
    readonly items: KtList<OrganizationDTO>;
    readonly total: number;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationPageResultDTO": unique symbol;
    } & PageDTO<unknown>["__doNotUseOrImplementIt"];
}
export declare class OrganizationPageResult implements OrganizationPageResultDTO {
    constructor(items: KtList<Organization>, total: number);
    get items(): KtList<Organization>;
    get total(): number;
    copy(items?: KtList<Organization>, total?: number): OrganizationPageResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationPageResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationPageResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationPageResult;
    }
}
export declare interface OrganizationRefGetQueryDTO extends Query {
    readonly id: string;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationRefGetQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class OrganizationRefGetQuery implements OrganizationRefGetQueryDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): OrganizationRefGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationRefGetQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationRefGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationRefGetQuery;
    }
}
export declare interface OrganizationRefGetResultDTO extends Event {
    readonly item: Nullable<OrganizationRefDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationRefGetResultDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationRefGetResult implements OrganizationRefGetResultDTO {
    constructor(item: Nullable<OrganizationRef>);
    get item(): Nullable<OrganizationRef>;
    copy(item?: Nullable<OrganizationRef>): OrganizationRefGetResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationRefGetResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationRefGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationRefGetResult;
    }
}
export declare interface OrganizationRefListQueryDTO extends Query {
    readonly withDisabled: boolean;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationRefListQueryDTO": unique symbol;
    } & Query["__doNotUseOrImplementIt"];
}
export declare class OrganizationRefListQuery implements OrganizationRefListQueryDTO {
    constructor(withDisabled?: boolean);
    get withDisabled(): boolean;
    copy(withDisabled?: boolean): OrganizationRefListQuery;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationRefListQueryDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationRefListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationRefListQuery;
    }
}
export declare interface OrganizationRefListResultDTO extends Event {
    readonly items: KtList<OrganizationRefDTO>;
    readonly __doNotUseOrImplementIt: {
        readonly "io.komune.im.f2.organization.domain.query.OrganizationRefListResultDTO": unique symbol;
    } & Event["__doNotUseOrImplementIt"];
}
export declare class OrganizationRefListResult implements OrganizationRefListResultDTO {
    constructor(items: KtList<OrganizationRef>);
    get items(): KtList<OrganizationRef>;
    copy(items?: KtList<OrganizationRef>): OrganizationRefListResult;
    toString(): string;
    hashCode(): number;
    equals(other: Nullable<unknown>): boolean;
    readonly __doNotUseOrImplementIt: OrganizationRefListResultDTO["__doNotUseOrImplementIt"];
}
export declare namespace OrganizationRefListResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OrganizationRefListResult;
    }
}