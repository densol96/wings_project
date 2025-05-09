"use server";

import { FormState, HttpMethod } from "@/types";
import { fetchWithSetup } from "@/utils";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

// Template for SEVRER ACTIONS
export const serverFetchAction = async ({
  endpoint,
  method,
  body,
  revalidatePathUrl,
}: {
  body?: Record<string, any>;
  endpoint: string;
  method: HttpMethod;
  revalidatePathUrl?: string;
}): Promise<FormState> => {
  try {
    const response = await fetchWithSetup(endpoint, {
      method,
      body,
      headers: {
        Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      },
    });

    const data = await response.json();
    if (response.ok) {
      revalidatePath(revalidatePathUrl || "/admin", "layout");
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
