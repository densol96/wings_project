"use server";

import { FormState } from "@/types";
import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

export const updatePassword = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/users/${formDataSerialised.id}/update-password`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      },
      body: JSON.stringify(formDataSerialised),
    });

    const data = await response.json();
    if (response.ok) {
      revalidatePath("/admin", "layout");
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
