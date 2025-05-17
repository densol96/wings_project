"use server";

import { basicErrorText, fetchWithSetup } from "@/utils";
import { cookies, headers } from "next/headers";
import { redirect } from "next/navigation";
import { getHeaders } from "./getHeaders";

export const adminFetch = async <T>(adminEndpoint: string) => {
  const response = await fetchWithSetup(`admin/${adminEndpoint}`, {
    headers: await getHeaders(),
  });

  if (response.status === 401) redirect("/admin/login?expired=true");
  if (response.status === 403) redirect(`/admin/unauthorised?pathname=${headers().get("X-Current-Url")}`);

  let data;
  try {
    data = await response.json();
  } catch (e) {
    try {
      data = { message: (await response.text()) || basicErrorText() };
    } catch (e) {
      data = { message: basicErrorText() };
    }
  }

  if (!response.ok) throw new Error(data?.message);
  return data as T;
};
