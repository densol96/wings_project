export type FormState = { error?: { message: string }; errors?: Record<string, string | string[]> } | null;

export type UserSessionInfoDto = {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  authorities: Set<string>;
};
