import { SortDirection } from "../common";

export type UserAdminDto = {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  roles: string[];
  status: string;
  loginAttempts: number;
  lastActivityDateTime: string;
  joinDateTime: string;
};

export type UserSort = "joinDateTime" | "lastActivityDateTime";

export type UserStatus = "inactive" | "active" | "all";

export type UsersSearchParams = {
  sort: UserSort | null;
  direction: SortDirection | null;
  status: UserStatus | null;
};

export type UserDetailsDto = {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  accountLocked: boolean;
  accountBanned: boolean;
  roles?: number[];
};

export type RoleDto = {
  id: number;
  name: string;
};

export type PermissionDto = {
  id: number;
  name: string;
  label: string;
};

export type DetailedRoleDto = {
  id: number;
  name: string;
  permissions: PermissionDto[];
};

export type PermissionSearchParams = {
  permissions: string | string[];
};

export enum SecurityEventType {
  NEW_USER_REGISTERED = "NEW_USER_REGISTERED",
  LOGIN_SUCCESS = "LOGIN_SUCCESS",
  LOGIN_FAILED = "LOGIN_FAILED",
  PASSWORD_CHANGED = "PASSWORD_CHANGED",
  AFTER_HOURS_ACCESS = "AFTER_HOURS_ACCESS",
  ACCESS_FROM_NEW_IP = "ACCESS_FROM_NEW_IP",
  UNUSUAL_USER_AGENT = "UNUSUAL_USER_AGENT",
  ACCOUNT_LOCKED = "ACCOUNT_LOCKED",
  ACCOUNT_UNLOCKED = "ACCOUNT_UNLOCKED",
  ACCOUNT_BANNED = "ACCOUNT_BANNED",
  ACCOUNT_UNBANNED = "ACCOUNT_UNBANNED",
  EMAIL_CHANGED = "EMAIL_CHANGED",
}

export type SecurityEventDto = {
  id: number;
  username: string;
  eventType: string;
  dateTime: string;
  ipAddress: string;
  userAgent: string;
  requestUri: string;
  additionalInfo: string;
};

export type EventsSearchParams = {
  q?: string;
  page?: string;
  type?: SecurityEventType;
};
