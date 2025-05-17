"use server";

import { FormState, HttpMethod } from "@/types";
import { fetchWithSetup } from "@/utils";
import { revalidatePath } from "next/cache";
import { headers } from "next/headers";
import { getHeaders } from "./getHeaders";
import { redirect } from "next/navigation";

// Template for SEVRER ACTIONS
export const serverFetchAction = async ({
  endpoint,
  method,
  body,
  revalidatePathUrl,
  alternativeOk,
  isLogin = false,
}: {
  body?: Record<string, any>;
  endpoint: string;
  method: HttpMethod;
  revalidatePathUrl?: string;
  alternativeOk?: (data: any) => void;
  isLogin?: boolean;
}): Promise<FormState> => {
  try {
    const response = await fetchWithSetup(endpoint, {
      method,
      body,
      headers: await getHeaders(),
    });

    if (!isLogin && response.status === 401) redirect("/admin/login?expired=true");
    if (!isLogin && response.status === 403) redirect(`/admin/unauthorised?pathname=${endpoint}`);

    const data = await response.json();

    if (response.ok) {
      if (alternativeOk) {
        alternativeOk(data);
      } else {
        revalidatePath(revalidatePathUrl || "/admin", "layout");
      }
      return { success: data };
    }
    if (response.status === 400) {
      return { errors: data };
    } else {
      return { error: data };
    }
  } catch (e) {
    // network error
    console.log(e);
    return { error: { message: "Radās neparedzēta iekšēja kļūda. Lūdzu, mēģiniet vēlreiz vēlāk." } };
  }
};
