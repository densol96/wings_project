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
  name: string;
  label: string;
};

export type DetailedRoleDto = {
  id: number;
  name: string;
  permissions: PermissionDto[];
};
