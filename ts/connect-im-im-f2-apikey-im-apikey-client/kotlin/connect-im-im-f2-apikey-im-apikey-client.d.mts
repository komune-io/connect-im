declare function KtSingleton<T>(): T & (abstract new() => any);
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
}
export declare class FeatureDefineCommand implements FeatureDefineCommandDTO {
    constructor(identifier: string, description: string);
    get identifier(): string;
    get description(): string;
    copy(identifier?: string, description?: string): FeatureDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace FeatureDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureDefineCommand;
    }
}
export declare interface FeatureDefinedEventDTO extends Event {
    readonly identifier: string;
}
export declare class FeatureDefinedEvent implements FeatureDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): FeatureDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
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
    equals(other?: any): boolean;
}
export declare namespace Feature {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Feature;
    }
}
export declare interface FeatureGetQueryDTO {
    readonly identifier: string;
}
export declare class FeatureGetQuery implements FeatureGetQueryDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): FeatureGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace FeatureGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureGetQuery;
    }
}
export declare interface FeatureGetResultDTO {
    readonly item?: FeatureDTO | null;
}
export declare class FeatureGetResult implements FeatureGetResultDTO {
    constructor(item?: Feature | null);
    get item(): Feature | null;
    copy(item?: Feature | null): FeatureGetResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace FeatureGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureGetResult;
    }
}
export declare interface FeatureListQueryDTO {
}
export declare class FeatureListQuery implements FeatureListQueryDTO {
    constructor();
}
export declare namespace FeatureListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => FeatureListQuery;
    }
}
export declare interface FeatureListResultDTO {
    readonly items: FeatureDTO[];
}
export declare class FeatureListResult implements FeatureListResultDTO {
    constructor(items: Feature[]);
    get items(): Feature[];
    copy(items?: Feature[]): FeatureListResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
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
}
export declare interface PermissionDefineCommandDTO {
    readonly identifier: string;
    readonly description: string;
    readonly features?: string[][] | null;
}
export declare class PermissionDefineCommand implements PermissionDefineCommandDTO {
    constructor(identifier: string, description: string, features?: string[][] | null);
    get identifier(): string;
    get description(): string;
    get features(): string[][] | null;
    copy(identifier?: string, description?: string, features?: string[][] | null): PermissionDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace PermissionDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionDefineCommand;
    }
}
export declare interface PermissionDefinedEventDTO extends Event {
    readonly identifier: string;
}
export declare class PermissionDefinedEvent implements PermissionDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): PermissionDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
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
    readonly features?: string[][] | null;
}
export declare class Permission implements PermissionDTO {
    constructor(id: string, identifier: string, description: string, features?: string[][] | null, type?: string);
    get id(): string;
    get identifier(): string;
    get description(): string;
    get features(): string[][] | null;
    get type(): string;
    copy(id?: string, identifier?: string, description?: string, features?: string[][] | null, type?: string): Permission;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace Permission {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => Permission;
    }
}
export declare interface PermissionGetQueryDTO {
    readonly identifier: string;
}
export declare class PermissionGetQuery implements PermissionGetQueryDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): PermissionGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace PermissionGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionGetQuery;
    }
}
export declare interface PermissionGetResultDTO {
    readonly item?: PermissionDTO | null;
}
export declare class PermissionGetResult implements PermissionGetResultDTO {
    constructor(item?: Permission | null);
    get item(): Permission | null;
    copy(item?: Permission | null): PermissionGetResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace PermissionGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionGetResult;
    }
}
export declare interface PermissionListQueryDTO {
}
export declare class PermissionListQuery implements PermissionListQueryDTO {
    constructor();
}
export declare namespace PermissionListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => PermissionListQuery;
    }
}
export declare interface PermissionListResultDTO {
    readonly items: PermissionDTO[];
}
export declare class PermissionListResult implements PermissionListResultDTO {
    constructor(items: Permission[]);
    get items(): Permission[];
    copy(items?: Permission[]): PermissionListResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
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
    readonly targets: string[];
    readonly locale: Record<string, string>;
    readonly bindings?: Record<string, string[]> | null;
    readonly permissions?: string[] | null;
}
export declare class RoleDefineCommand implements RoleDefineCommandDTO {
    constructor(identifier: string, description: string, targets: string[], locale: Record<string, string>, bindings?: Record<string, string[]> | null, permissions?: string[] | null);
    get identifier(): string;
    get description(): string;
    get targets(): string[];
    get locale(): Record<string, string>;
    get bindings(): Record<string, string[]> | null;
    get permissions(): string[] | null;
    copy(identifier?: string, description?: string, targets?: string[], locale?: Record<string, string>, bindings?: Record<string, string[]> | null, permissions?: string[] | null): RoleDefineCommand;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace RoleDefineCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleDefineCommand;
    }
}
export declare interface RoleDefinedEventDTO extends Event {
    readonly identifier: string;
}
export declare class RoleDefinedEvent implements RoleDefinedEventDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): RoleDefinedEvent;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
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
    readonly targets: string[];
    readonly locale: Record<string, string>;
    readonly bindings: Record<string, RoleDTO[]>;
    readonly permissions: string[];
}
export declare class Role implements RoleDTO {
    constructor(id: string, identifier: string, description: string, targets: string[], locale: Record<string, string>, bindings: Record<string, Role[]>, permissions: string[], type?: string);
    get id(): string;
    get identifier(): string;
    get description(): string;
    get targets(): string[];
    get locale(): Record<string, string>;
    get bindings(): Record<string, Role[]>;
    get permissions(): string[];
    get type(): string;
    copy(id?: string, identifier?: string, description?: string, targets?: string[], locale?: Record<string, string>, bindings?: Record<string, Role[]>, permissions?: string[], type?: string): Role;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
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
}
export declare class RoleGetQuery implements RoleGetQueryDTO {
    constructor(identifier: string);
    get identifier(): string;
    copy(identifier?: string): RoleGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace RoleGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleGetQuery;
    }
}
export declare interface RoleGetResultDTO {
    readonly item?: RoleDTO | null;
}
export declare class RoleGetResult implements RoleGetResultDTO {
    constructor(item?: Role | null);
    get item(): Role | null;
    copy(item?: Role | null): RoleGetResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace RoleGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleGetResult;
    }
}
export declare interface RoleListQueryDTO {
    readonly target?: string | null;
}
export declare class RoleListQuery implements RoleListQueryDTO {
    constructor(target?: string | null);
    get target(): string | null;
    copy(target?: string | null): RoleListQuery;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace RoleListQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleListQuery;
    }
}
export declare interface RoleListResultDTO {
    readonly items: RoleDTO[];
}
export declare class RoleListResult implements RoleListResultDTO {
    constructor(items: Role[]);
    get items(): Role[];
    copy(items?: Role[]): RoleListResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace RoleListResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => RoleListResult;
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
export declare interface ApiKeyOrganizationAddCommandDTO extends Command {
    readonly organizationId: string;
    readonly name: string;
    readonly secret?: string | null;
    readonly roles: string[];
}
export declare class ApiKeyOrganizationAddKeyCommand implements ApiKeyOrganizationAddCommandDTO {
    constructor(organizationId: string, name: string, secret: string | null | undefined, roles: string[]);
    get organizationId(): string;
    get name(): string;
    get secret(): string | null;
    get roles(): string[];
    copy(organizationId?: string, name?: string, secret?: string | null, roles?: string[]): ApiKeyOrganizationAddKeyCommand;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKeyOrganizationAddKeyCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyOrganizationAddKeyCommand;
    }
}
export declare interface ApiKeyAddedEventDTO extends Event {
    readonly organizationId: string;
    readonly id: string;
    readonly keyIdentifier: string;
    readonly keySecret: string;
}
export declare class ApiKeyOrganizationAddedEvent implements ApiKeyAddedEventDTO {
    constructor(id: string, organizationId: string, keyIdentifier: string, keySecret: string);
    get id(): string;
    get organizationId(): string;
    get keyIdentifier(): string;
    get keySecret(): string;
    copy(id?: string, organizationId?: string, keyIdentifier?: string, keySecret?: string): ApiKeyOrganizationAddedEvent;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKeyOrganizationAddedEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyOrganizationAddedEvent;
    }
}
export declare interface ApikeyRemoveCommandDTO extends Command {
    readonly id: string;
}
export declare class ApikeyRemoveCommand implements ApikeyRemoveCommandDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): ApikeyRemoveCommand;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApikeyRemoveCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApikeyRemoveCommand;
    }
}
export declare interface ApikeyRemoveEventDTO extends Event {
    readonly id: string;
    readonly organizationId: string;
}
export declare class ApikeyRemoveEvent implements ApikeyRemoveEventDTO {
    constructor(id: string, organizationId: string);
    get id(): string;
    get organizationId(): string;
    copy(id?: string, organizationId?: string): ApikeyRemoveEvent;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApikeyRemoveEvent {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApikeyRemoveEvent;
    }
}
export declare interface ApiKeyDTO {
    readonly id: string;
    readonly name: string;
    readonly identifier: string;
    readonly roles: RoleDTO[];
    readonly creationDate: number;
}
export declare class ApiKey implements ApiKeyDTO {
    constructor(id: string, name: string, identifier: string, roles: Role[], creationDate: number);
    get id(): string;
    get name(): string;
    get identifier(): string;
    get roles(): Role[];
    get creationDate(): number;
    copy(id?: string, name?: string, identifier?: string, roles?: Role[], creationDate?: number): ApiKey;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKey {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKey;
    }
}
export declare abstract class ApiKeyPolicies {
    static readonly getInstance: () => typeof ApiKeyPolicies.$metadata$.type;
    private constructor();
}
export declare namespace ApiKeyPolicies {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            canGet(authedUser: AuthedUserDTO, apikeyId: string): boolean;
            canPage(authedUser: AuthedUserDTO): boolean;
            canCreate(authedUser: AuthedUserDTO): boolean;
            canDelete(authedUser: AuthedUserDTO): boolean;
            private constructor();
        }
    }
}
export declare interface ApiKeyGetQueryDTO extends Query {
    readonly id: string;
}
export declare class ApiKeyGetQuery implements ApiKeyGetQueryDTO {
    constructor(id: string);
    get id(): string;
    copy(id?: string): ApiKeyGetQuery;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKeyGetQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyGetQuery;
    }
}
export declare interface ApiKeyGetResultDTO extends Event {
    readonly item?: ApiKeyDTO | null;
}
export declare class ApiKeyGetResult implements ApiKeyGetResultDTO {
    constructor(item?: ApiKey | null);
    get item(): ApiKey | null;
    copy(item?: ApiKey | null): ApiKeyGetResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKeyGetResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyGetResult;
    }
}
export declare interface ApiKeyPageQueryDTO extends Query {
    readonly search?: string | null;
    readonly organizationId?: string | null;
    readonly role?: string | null;
    readonly attributes?: Record<string, string> | null;
    readonly withDisabled?: boolean | null;
    readonly limit?: number | null;
    readonly offset?: number | null;
}
export declare class ApiKeyPageQuery implements ApiKeyPageQueryDTO {
    constructor(search?: string | null, organizationId?: string | null, role?: string | null, attributes?: Record<string, string> | null, withDisabled?: boolean | null, limit?: number | null, offset?: number | null);
    get search(): string | null;
    get organizationId(): string | null;
    get role(): string | null;
    get attributes(): Record<string, string> | null;
    get withDisabled(): boolean | null;
    get limit(): number | null;
    get offset(): number | null;
    copy(search?: string | null, organizationId?: string | null, role?: string | null, attributes?: Record<string, string> | null, withDisabled?: boolean | null, limit?: number | null, offset?: number | null): ApiKeyPageQuery;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKeyPageQuery {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyPageQuery;
    }
}
export declare interface ApiKeyPageResultDTO extends PageDTO<ApiKeyDTO> {
    readonly items: ApiKeyDTO[];
    readonly total: number;
}
export declare class ApiKeyPageResult implements ApiKeyPageResultDTO {
    constructor(items: ApiKey[], total: number);
    get items(): ApiKey[];
    get total(): number;
    copy(items?: ApiKey[], total?: number): ApiKeyPageResult;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace ApiKeyPageResult {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyPageResult;
    }
}
export type F2ClientType = "HTTP";
export declare namespace F2ClientType {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => F2ClientType;
    }
}
export declare interface F2Client {
    readonly type: F2ClientType;
}
/** @deprecated  */
export declare const initHook: {
    get(): any;
};
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
export declare interface HttpClientBuilder {
    build(urlBase: string): HttpF2Client;
}
export declare class HttpF2Client implements F2Client {
    private constructor();
    get urlBase(): string;
    get type(): F2ClientType;
}
export declare namespace HttpF2Client {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => HttpF2Client;
    }
}
export declare interface F2UploadCommand<COMMAND> {
    readonly command: COMMAND;
    readonly fileMap: Record<string, F2FilePart[]>;
}
export declare class F2UploadSingleCommand<COMMAND> implements F2UploadCommand<COMMAND> {
    constructor(command: COMMAND, file: F2FilePart);
    get command(): COMMAND;
    get file(): F2FilePart;
    get fileMap(): Record<string, F2FilePart[]>;
    copy(command?: COMMAND, file?: F2FilePart): F2UploadSingleCommand<COMMAND>;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace F2UploadSingleCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <COMMAND>() => F2UploadSingleCommand<COMMAND>;
    }
}
export declare class F2UploadMultipleCommand<COMMAND> implements F2UploadCommand<COMMAND> {
    constructor(command: COMMAND, files: F2FilePart[]);
    get command(): COMMAND;
    get files(): F2FilePart[];
    get fileMap(): Record<string, F2FilePart[]>;
    copy(command?: COMMAND, files?: F2FilePart[]): F2UploadMultipleCommand<COMMAND>;
    toString(): string;
    hashCode(): number;
    equals(other?: any): boolean;
}
export declare namespace F2UploadMultipleCommand {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new <COMMAND>() => F2UploadMultipleCommand<COMMAND>;
    }
}
export declare class F2FilePart {
    private constructor();
    get name(): string;
    get contentType(): string | null;
    static fromBytes(name: string, bytes: Int8Array, contentType?: string | null): F2FilePart;
}
export declare namespace F2FilePart {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => F2FilePart;
    }
}
export declare function httpClientBuilderDefault(): HttpClientBuilder;
export declare abstract class F2ClientBuilder {
    static readonly getInstance: () => typeof F2ClientBuilder.$metadata$.type;
    private constructor();
}
export declare namespace F2ClientBuilder {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        abstract class type extends KtSingleton<constructor>() {
            private constructor();
        }
        abstract class constructor {
            getHttp(url: string): F2Client;
            private constructor();
        }
    }
}
export declare class InvalidUrlException extends /* Exception */ Error {
    constructor(url: string);
}
export declare namespace InvalidUrlException {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => InvalidUrlException;
    }
}
export declare class ApiKeyClient /* implements ApiKeyApi */ {
    constructor(client: F2Client);
}
export declare namespace ApiKeyClient {
    /** @deprecated $metadata$ is used for internal purposes, please don't use it in your code, because it can be removed at any moment */
    namespace $metadata$ {
        const constructor: abstract new () => ApiKeyClient;
    }
}