"use server";

import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";

export const createUser = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  const numericRoleIds = formData.getAll("roles").map((id) => Number(id));
  const body = {
    ...formDataSerialised,
    roles: numericRoleIds,
    accountBanned: formDataSerialised.accountBanned === "on",
    accountLocked: formDataSerialised.accountLocked === "on",
  };
  return await serverFetchAction({
    endpoint: "admin/security/users",
    method: "POST",
    body,
  });
};
