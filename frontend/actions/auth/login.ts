"use server";

import { FormState } from "@/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { z } from "zod";

const schema = z
  .object({
    username: z
      .string()
      .nonempty({ message: "Lietotājvārds ir obligāts." })
      .min(3, { message: "Lietotājvārdam jābūt no 3 līdz 20 rakstzīmēm." })
      .max(20, { message: "Lietotājvārdam jābūt no 3 līdz 20 rakstzīmēm." }),
    password: z
      .string()
      .nonempty({ message: "Parole ir obligāta." })
      .min(6, { message: "Parolei jābūt no 6 līdz 100 rakstzīmēm." })
      .max(50, { message: "Parolei jābūt no 6 līdz 100 rakstzīmēm." }),
  })
  .required();

export const login = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = {
    username: formData.get("username"),
    password: formData.get("password"),
  };

  const parsed = schema.safeParse(formDataSerialised);

  if (!parsed.success) {
    return {
      errors: parsed.error.flatten().fieldErrors,
    };
  }
  const { username, password } = parsed.data;
  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/auth/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });
    const data = await response.json();
    if (response.ok) {
      cookies().set("authToken", data.jwt, {
        httpOnly: true,
        secure: process.env.MODE === "prod",
        sameSite: "strict",
        path: "/",
      });
      redirect("/admin/dashboard");
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
