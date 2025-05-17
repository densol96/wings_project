"use server";

import { FormState } from "@/types";
import { z } from "zod";
import { serverFetchAction } from "../helpers/serverFetchAction";

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
  .required()
  .refine((data) => data.password !== data.oldPassword, {
    message: "Jaunā parole nevar būt tāda pati kā pašreizējā parole.",
    path: ["password"],
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Jaunā parole un apstiprinājums nesakrīt.",
    path: ["confirmPassword"],
  });

export const changePassword = async (prevState: FormState, formData: FormData): Promise<FormState> => {
  const formDataSerialised = Object.fromEntries(formData.entries());
  const parsed = schema.safeParse(formDataSerialised);
  if (!parsed.success) {
    return {
      errors: parsed.error.flatten().fieldErrors,
    };
  }
  return await serverFetchAction({
    endpoint: "auth/change-password",
    method: "PATCH",
    body: parsed.data,
  });
};
