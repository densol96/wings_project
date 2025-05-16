"use server";

import { basicErrorText, fetchWithSetup } from "@/utils";
import { cookies, headers } from "next/headers";
import { redirect } from "next/navigation";

export const adminFetch = async <T>(adminEndpoint: string) => {
  const response = await fetchWithSetup(`admin/${adminEndpoint}`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      "User-Agent": headers().get("user-agent") ?? "",
      "X-Forwarded-For": headers().get("x-forwarded-for") ?? "",
    },

    /**
     * I am doing the authorisation related checks in th middleware => if the user hits any page - it is valid. =>
     * Data can actually be cached and revalidated on request
     */
    // additionalOptions: {
    //   cache: "no-store", // need to always be stale
    // },
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
