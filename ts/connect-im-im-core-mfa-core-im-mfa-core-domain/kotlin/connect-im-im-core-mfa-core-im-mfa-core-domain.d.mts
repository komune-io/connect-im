declare function KtSingleton<T>(): T & (abstract new() => any);
export declare interface AuthedUserDTO {
    readonly id: string;
    readonly identifier?: string | null;
    readonly memberOf?: string | null;
    readonly roles?: Array<string> | null;
    readonly acr?: string | null;
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
}
export declare class Address implements AddressDTO {
    constructor(street: string, postalCode: string, city: string);
    get street(): string;
    get postalCode(): string;
    get city(): string;
    copy(street?: string, postalCode?: string, city?: string): Address;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace Address {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Address;
    }
}
export declare interface F2Consumer<T> {
}
export declare interface F2Function<in T, out R> {
}
export declare interface F2Supplier<R> {
}
export declare interface F2SupplierSingle<R> {
}
export declare class Batch {
    constructor(size?: number, concurrency?: number);
    get size(): number;
    get concurrency(): number;
    copy(size?: number, concurrency?: number): Batch;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace Batch {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Batch;
    }
}
export declare function invokeF2Function<T, R>(fnc: F2Function<T, R>, msgs: Array<T>): Promise<Array<R>>;
export declare function invokeF2Consumer<T>(fnc: F2Consumer<T>, msgs: Array<T>): Promise<void>;
export declare function invokeF2Supplier<R>(fnc: F2Supplier<R>): Promise<Array<R>>;
export declare function invokeF2SupplierSingle<R>(fnc: F2SupplierSingle<R>): Promise<R>;
export declare interface Command extends Message {
}
export declare interface Event extends Message {
}
export declare interface Message {
}
export declare interface Problem extends Message {
}
export declare interface Query extends Message {
}
export declare interface EnvelopeDTO<T> extends WithEnvelopeId, WithEnvelopeData<T>, Message {
    readonly id: string;
    readonly data: T;
    readonly type: string;
    readonly datacontenttype?: string | null;
    readonly specversion?: string | null;
    readonly source?: string | null;
    readonly time?: string | null;
}
export declare class Envelope<T> implements EnvelopeDTO<T> {
    constructor(id: string, data: T, type: string, datacontenttype?: string | null, specversion?: string | null, source?: string | null, time?: string | null);
    get id(): string;
    get data(): T;
    get type(): string;
    get datacontenttype(): string | null;
    get specversion(): string | null;
    get source(): string | null;
    get time(): string | null;
}
export declare namespace Envelope {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <T>() => Envelope<T>;
    }
}
export declare interface WithEnvelopeData<T> {
    readonly data: T;
}
export declare interface WithEnvelopeId {
    readonly id: string;
}
export declare interface F2ErrorDTO extends Problem {
    readonly id?: string | null;
    readonly timestamp: string;
    readonly code: number;
    readonly requestId?: string | null;
    readonly message: string;
    readonly refusal?: string | null;
    readonly parameters: Record<string, string>;
    readonly citers: string[];
}
export declare class F2Error implements F2ErrorDTO {
    constructor(message: string, id?: string | null, timestamp?: string, code?: number, requestId?: string | null, refusal?: string | null, parameters?: Record<string, string>, citers?: string[]);
    get message(): string;
    get id(): string | null;
    get timestamp(): string;
    get code(): number;
    get requestId(): string | null;
    get refusal(): string | null;
    get parameters(): Record<string, string>;
    get citers(): string[];
    toString(): string;
}
export declare namespace F2Error {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => F2Error;
    }
}
export declare function isRefusal(error: F2ErrorDTO): boolean;
export declare function refusalCode(error: F2ErrorDTO): string;
export declare abstract class HttpRefusal {
    private constructor();
    static get INVALID_PAYLOAD(): HttpRefusal & {
        get name(): "INVALID_PAYLOAD";
        get ordinal(): 0;
    };
    static get UNAUTHENTICATED(): HttpRefusal & {
        get name(): "UNAUTHENTICATED";
        get ordinal(): 1;
    };
    static get FORBIDDEN(): HttpRefusal & {
        get name(): "FORBIDDEN";
        get ordinal(): 2;
    };
    static get NOT_FOUND(): HttpRefusal & {
        get name(): "NOT_FOUND";
        get ordinal(): 3;
    };
    static get METHOD_NOT_ALLOWED(): HttpRefusal & {
        get name(): "METHOD_NOT_ALLOWED";
        get ordinal(): 4;
    };
    static get CONFLICT(): HttpRefusal & {
        get name(): "CONFLICT";
        get ordinal(): 5;
    };
    static get PAYLOAD_TOO_LARGE(): HttpRefusal & {
        get name(): "PAYLOAD_TOO_LARGE";
        get ordinal(): 6;
    };
    static get UNSUPPORTED_MEDIA_TYPE(): HttpRefusal & {
        get name(): "UNSUPPORTED_MEDIA_TYPE";
        get ordinal(): 7;
    };
    static get UNPROCESSABLE(): HttpRefusal & {
        get name(): "UNPROCESSABLE";
        get ordinal(): 8;
    };
    static get TOO_MANY_REQUESTS(): HttpRefusal & {
        get name(): "TOO_MANY_REQUESTS";
        get ordinal(): 9;
    };
    static get UNEXPECTED(): HttpRefusal & {
        get name(): "UNEXPECTED";
        get ordinal(): 10;
    };
    static get UNAVAILABLE(): HttpRefusal & {
        get name(): "UNAVAILABLE";
        get ordinal(): 11;
    };
    static values(): [typeof HttpRefusal.INVALID_PAYLOAD, typeof HttpRefusal.UNAUTHENTICATED, typeof HttpRefusal.FORBIDDEN, typeof HttpRefusal.NOT_FOUND, typeof HttpRefusal.METHOD_NOT_ALLOWED, typeof HttpRefusal.CONFLICT, typeof HttpRefusal.PAYLOAD_TOO_LARGE, typeof HttpRefusal.UNSUPPORTED_MEDIA_TYPE, typeof HttpRefusal.UNPROCESSABLE, typeof HttpRefusal.TOO_MANY_REQUESTS, typeof HttpRefusal.UNEXPECTED, typeof HttpRefusal.UNAVAILABLE];
    static valueOf(value: string): HttpRefusal;
    get name(): "INVALID_PAYLOAD" | "UNAUTHENTICATED" | "FORBIDDEN" | "NOT_FOUND" | "METHOD_NOT_ALLOWED" | "CONFLICT" | "PAYLOAD_TOO_LARGE" | "UNSUPPORTED_MEDIA_TYPE" | "UNPROCESSABLE" | "TOO_MANY_REQUESTS" | "UNEXPECTED" | "UNAVAILABLE";
    get ordinal(): 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11;
    get status(): number;
    get refusal(): string;
}
export declare namespace HttpRefusal {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => HttpRefusal;
    }
    abstract class Companion extends KtSingleton<Companion.$metadata$.constructor>() {
        private constructor();
    }
    namespace Companion {
        /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
        namespace $metadata$ {
            abstract class constructor {
                get DOMAIN(): string;
                of(status: number): HttpRefusal;
                private constructor();
            }
        }
    }
}
export declare class F2Exception extends /* RuntimeException */ Error {
    constructor(error: F2ErrorDTO, cause?: Error | null);
    get error(): F2ErrorDTO;
}
export declare namespace F2Exception {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => F2Exception;
    }
    abstract class Companion extends KtSingleton<Companion.$metadata$.constructor>() {
        private constructor();
    }
    namespace Companion {
        /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
        namespace $metadata$ {
            abstract class constructor {
                invoke(message: string, id?: string, requestId?: string, code?: number, cause?: Error | null): F2Exception;
                private constructor();
            }
        }
    }
}
export declare interface Match<T> {
    readonly negative: boolean;
    map<R>(transform: (value: T) => R): Match<R>;
    not(): Match<T>;
    and(match: Match<T>): Match<T>;
    or(match: Match<T>): Match<T>;
}
export declare interface SortDTO {
    readonly property: string;
    readonly ascending: boolean;
    readonly nullsFirst?: boolean | null;
}
export declare interface PageDTO<out OBJECT> {
    readonly total: number;
    readonly items: OBJECT[];
}
export declare class Page<out OBJECT> implements PageDTO<OBJECT> {
    constructor(total: number, items: OBJECT[]);
    get total(): number;
    get items(): OBJECT[];
}
export declare namespace Page {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <OBJECT>() => Page<OBJECT>;
    }
}
export declare interface PageQueryDTO extends Query {
    readonly pagination?: OffsetPaginationDTO | null;
}
export declare interface PageQueryResultDTO<out OBJECT> extends Event, PageDTO<OBJECT> {
    readonly total: number;
    readonly items: OBJECT[];
    readonly pagination?: OffsetPaginationDTO | null;
}
export declare class PageQuery implements PageQueryDTO {
    constructor(pagination?: OffsetPaginationDTO | null);
    get pagination(): OffsetPaginationDTO | null;
}
export declare namespace PageQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PageQuery;
    }
}
export declare class PageQueryResult<out OBJECT> implements PageQueryResultDTO<OBJECT> {
    constructor(pagination: OffsetPagination | null, total: number, items: OBJECT[]);
    get pagination(): OffsetPagination | null;
    get total(): number;
    get items(): OBJECT[];
}
export declare namespace PageQueryResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <OBJECT>() => PageQueryResult<OBJECT>;
    }
}
export declare interface Pagination {
}
export declare interface OffsetPaginationDTO extends Pagination {
    readonly offset: number;
    readonly limit: number;
}
export declare interface PagePaginationDTO extends Pagination {
    readonly page?: number | null;
    readonly size?: number | null;
}
export declare class OffsetPagination implements OffsetPaginationDTO {
    constructor(offset: number, limit: number);
    get offset(): number;
    get limit(): number;
}
export declare namespace OffsetPagination {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => OffsetPagination;
    }
}
export declare class PagePagination implements PagePaginationDTO {
    constructor(page?: number | null, size?: number | null);
    get page(): number | null;
    get size(): number | null;
}
export declare namespace PagePagination {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PagePagination;
    }
}
export declare abstract class AuthRealm {
    private constructor();
    get serverUrl(): string;
    get realmId(): string;
    get clientId(): string;
    get redirectUrl(): string | null;
}
export declare namespace AuthRealm {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => AuthRealm;
    }
}
export declare class AuthRealmPassword extends AuthRealm.$metadata$.constructor {
    constructor(serverUrl: string, realmId: string, redirectUrl: string, clientId: string, username: string, password: string);
    get serverUrl(): string;
    get realmId(): string;
    get redirectUrl(): string;
    get clientId(): string;
    get username(): string;
    get password(): string;
    copy(serverUrl?: string, realmId?: string, redirectUrl?: string, clientId?: string, username?: string, password?: string): AuthRealmPassword;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace AuthRealmPassword {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => AuthRealmPassword;
    }
}
export declare class AuthRealmClientSecret extends AuthRealm.$metadata$.constructor {
    constructor(serverUrl: string, realmId: string, clientId: string, redirectUrl: string | null | undefined, clientSecret: string, isPublic?: boolean);
    get serverUrl(): string;
    get realmId(): string;
    get clientId(): string;
    get redirectUrl(): string | null;
    get clientSecret(): string;
    get isPublic(): boolean;
    copy(serverUrl?: string, realmId?: string, clientId?: string, redirectUrl?: string | null, clientSecret?: string, isPublic?: boolean): AuthRealmClientSecret;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace AuthRealmClientSecret {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => AuthRealmClientSecret;
    }
}
export declare class TokenInfo {
    constructor(accessToken: string, expiresIn: number, refreshExpiresIn: number | null | undefined, tokenType: string, scope?: string | null, idToken?: string | null, refreshToken?: string | null);
    get accessToken(): string;
    get expiresIn(): number;
    get refreshExpiresIn(): number | null;
    get tokenType(): string;
    get scope(): string | null;
    get idToken(): string | null;
    get refreshToken(): string | null;
    copy(accessToken?: string, expiresIn?: number, refreshExpiresIn?: number | null, tokenType?: string, scope?: string | null, idToken?: string | null, refreshToken?: string | null): TokenInfo;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace TokenInfo {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => TokenInfo;
    }
}
export declare abstract class ImMfaPasswordOtpFlowAcr {
    private constructor();
    static get PASSWORD_ONLY(): ImMfaPasswordOtpFlowAcr & {
        get name(): "PASSWORD_ONLY";
        get ordinal(): 0;
    };
    static get PASSWORD_OTP(): ImMfaPasswordOtpFlowAcr & {
        get name(): "PASSWORD_OTP";
        get ordinal(): 1;
    };
    static values(): [typeof ImMfaPasswordOtpFlowAcr.PASSWORD_ONLY, typeof ImMfaPasswordOtpFlowAcr.PASSWORD_OTP];
    static valueOf(value: string): ImMfaPasswordOtpFlowAcr;
    get name(): "PASSWORD_ONLY" | "PASSWORD_OTP";
    get ordinal(): 0 | 1;
    get key(): string;
    get level(): number;
}
export declare namespace ImMfaPasswordOtpFlowAcr {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ImMfaPasswordOtpFlowAcr;
    }
    abstract class Companion extends KtSingleton<Companion.$metadata$.constructor>() {
        private constructor();
    }
    namespace Companion {
        /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
        namespace $metadata$ {
            abstract class constructor {
                asKeycloakMap(): Record<string, string>;
                private constructor();
            }
        }
    }
}