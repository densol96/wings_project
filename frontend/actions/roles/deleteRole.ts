"use server";

import { FormState } from "@/types";
import { serverFetchAction } from "../serverFetchAction";

export const deleteRole = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  return await serverFetchAction({
    endpoint: `admin/roles/${formData.get("id")}`,
    method: "DELETE",
  });
};

export default deleteRole;
