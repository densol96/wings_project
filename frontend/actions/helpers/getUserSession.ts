"use server";

import { UserSessionInfoDto } from "@/types";
import { headers } from "next/headers";

export const getUserSession = async (token: string): Promise<UserSessionInfoDto | null> => {
  try {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/auth/user-data`, {
      headers: {
        Authorization: `Bearer ${token}`,
        "User-Agent": headers().get("user-agent") ?? "",
        "X-Forwarded-For": headers().get("x-forwarded-for") ?? "",
      },
      cache: "no-store",
    });
    if (!response.ok) throw Error();
    return (await response.json()) as UserSessionInfoDto;
  } catch (e) {
    return null;
  }
};
