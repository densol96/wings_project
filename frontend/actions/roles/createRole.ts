"use server";
import { FormState } from "@/types";
import { serverFetchAction } from "../serverFetchAction";

export const createRole = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  const numericPermissionIds = formData.getAll("permissions").map((id) => Number(id));
  const body = {
    ...formDataSerialised,
    permissionIds: numericPermissionIds,
  };
  return await serverFetchAction({
    endpoint: "admin/roles",
    method: "POST",
    body,
  });
};

export default createRole;
