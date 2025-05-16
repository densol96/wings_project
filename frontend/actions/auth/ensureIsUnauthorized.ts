"use server";

import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { getUserSession } from "../getUserSession";

export const ensureIsUnauthorized = async () => {
  const token = cookies().get("authToken")?.value;
  if (!token) return;
  const user = await getUserSession(token);
  if (user) redirect("/admin/dashboard");
};
