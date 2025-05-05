export type FormState = {
  success?: { message: string };
  error?: { message: string };
  errors?: Record<string, string | string[]>;
} | null;

export enum Permission {
  MANAGE_NEWS = "MANAGE_NEWS",
  MANAGE_SECURITY = "MANAGE_SECURITY",
  MANAGE_PRODUCTS = "MANAGE_PRODUCTS",
  MANAGE_ORDERS = "MANAGE_ORDERS",
}

export type UserSessionInfoDto = {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  authorities: Permission[];
};
