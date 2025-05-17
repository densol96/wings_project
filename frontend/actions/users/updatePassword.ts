"use server";

import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

export const updatePassword = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  return await serverFetchAction({
    endpoint: `admin/security/users/${formDataSerialised.id}/update-password`,
    method: "PATCH",
    body: formDataSerialised,
  });
};
