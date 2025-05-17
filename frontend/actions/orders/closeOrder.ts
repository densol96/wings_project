"use server";
import { FormState } from "@/types";
import { serverFetchAction } from "../helpers/serverFetchAction";

export const closeOrder = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const { id } = Object.fromEntries(formData.entries());
  return await serverFetchAction({
    endpoint: `admin/orders/${id}/close`,
    method: "PATCH",
  });
};

export default closeOrder;
