"use server";

import { cookies } from "next/headers";
import { UserSessionInfoDto } from "@/types";
import { logout } from "./logout";
import { getUserSession } from "@/utils";
import { redirect } from "next/navigation";

export const ensureIsUnauthorized = async () => {
  const token = cookies().get("authToken")?.value;
  if (!token) return;
  const user = await getUserSession(token);
  if (user) redirect("/admin/dashboard");
};
