"use server";

import { FormState } from "@/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { z } from "zod";

const schema = z
  .object({
    oldPassword: z
      .string()
      .nonempty({ message: "Pašreizējā parole ir obligāta." })
      .min(6, { message: "Pašreizējāi parolei jābūt no 6 līdz 100 rakstzīmēm." })
      .max(50, { message: "Pašreizējāi parolei jābūt no 6 līdz 100 rakstzīmēm." }),
    password: z
      .string()
      .nonempty({ message: "Jaunā parole ir obligāta." })
      .min(6, { message: "Jaunāi parolei jābūt no 6 līdz 100 rakstzīmēm." })
      .max(50, { message: "Jaunāi parolei jābūt no 6 līdz 100 rakstzīmēm." }),
    confirmPassword: z
      .string()
      .nonempty({ message: "Jaunās paroles apstiprināšana ir obligāta." })
      .min(6, { message: "Paroles apstiprināšanai jābūt no 6 līdz 100 rakstzīmēm." })
      .max(50, { message: "Paroles apstiprināšanai jābūt no 6 līdz 100 rakstzīmēm." }),
  })
  .required();

export const changePassword = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  const parsed = schema.safeParse(formDataSerialised);
  if (!parsed.success) {
    return {
      errors: parsed.error.flatten().fieldErrors,
    };
  }

  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/auth/change-password`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      },
      body: JSON.stringify(parsed.data),
      cache: "no-store",
    });
    const data = await response.json();
    if (response.ok) {
      cookies().delete("authToken");
      return data; //.message
    }
    if (response.status === 400) {
      return { errors: data };
    } else {
      return { error: data };
    }
  } catch (e) {
    // network error
    return { error: { message: "Radās neparedzēta iekšēja kļūda. Lūdzu, mēģiniet vēlreiz vēlāk." } };
  }
};
