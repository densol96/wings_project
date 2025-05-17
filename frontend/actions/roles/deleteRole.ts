"use server";

import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";

export const deleteRole = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  return await serverFetchAction({
    endpoint: `admin/security/roles/${formData.get("id")}`,
    method: "DELETE",
  });
};

export default deleteRole;
