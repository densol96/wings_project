"use server";
import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";

export const createRole = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  const numericPermissionIds = formData.getAll("permissions").map((id) => Number(id));
  const body = {
    ...formDataSerialised,
    permissionIds: numericPermissionIds,
  };
  return await serverFetchAction<FormState>({
    endpoint: "admin/security/roles",
    method: "POST",
    body,
  });
};

export default createRole;
