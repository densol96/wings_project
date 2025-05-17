"use server";

import { cookies, headers } from "next/headers";

export const getHeaders = () => {
  return {
    Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    "User-Agent": headers().get("user-agent") ?? "",
    "X-Forwarded-For": headers().get("x-forwarded-for") ?? "",
  };
};
