"use server";

import { FormState } from "@/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { z } from "zod";
import { serverFetchAction } from "../helpers/serverFetchAction";

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
  return await serverFetchAction({
    endpoint: "auth/login",
    method: "POST",
    body: parsed.data,
    alternativeOk: (data) => {
      cookies().set("authToken", data.jwt, {
        httpOnly: true,
        secure: process.env.MODE === "prod",
        sameSite: "strict",
        path: "/",
      });
      redirect("/admin/dashboard");
    },
    isLogin: true,
  });
};
