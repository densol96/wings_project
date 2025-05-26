"use server";

import { serverFetchAction } from "@/actions/helpers/serverFetchAction";
import { FormState } from "@/types";
import { revalidatePath } from "next/cache";

export const deleteImage = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  return await serverFetchAction({
    endpoint: `admin/${formData.get("entityName")}/images/${formData.get("id")}`,
    method: "DELETE",
    alternativeOk: () => {
      revalidatePath("/", "layout");
    },
  });
};

export default deleteImage;
