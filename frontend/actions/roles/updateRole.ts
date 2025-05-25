"use server";
import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";

export const updateRole = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  const numericRoleIds = formData.getAll("permissions").map((id) => Number(id));
  const body = {
    ...formDataSerialised,
    permissionIds: numericRoleIds,
  };
  return await serverFetchAction<FormState>({
    endpoint: `admin/security/roles/${formDataSerialised.id}`,
    method: "PUT",
    body: body,
  });
};

export default updateRole;
