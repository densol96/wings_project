"use server";
import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";

export const upgardeOrder = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const { additionalComment, translateMethod, id } = Object.fromEntries(formData.entries());

  return await serverFetchAction({
    endpoint: `admin/orders/${id}/upgrade`,
    method: "PATCH",
    body: { additionalComment, translateMethod },
  });
};

export default upgardeOrder;
