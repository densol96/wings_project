"use server";

import { cookies } from "next/headers";
import { UserSessionInfoDto } from "@/types";
import { getUserSession } from "@/utils";
import { redirect } from "next/navigation";

export const getUserSessionOrRedirect = async (): Promise<UserSessionInfoDto> => {
  const token = cookies().get("authToken")?.value;
  if (!token) redirect("/admin/login");
  const user = await getUserSession(token as string);
  if (!user) {
    // since Cookies can only be modified in a Server Action or Route Handler.
    redirect("/api/logout");
  }
  return user as UserSessionInfoDto;
};
