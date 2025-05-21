import { basicErrorText, fetchWithSetup } from "@/utils";

import { redirect } from "next/navigation";
import { getHeaders } from "./getHeaders";
import { accessHeaders } from "./accessHeaders";

export const adminFetch = async <T>(adminEndpoint: string) => {
  const response = await fetchWithSetup(`admin/${adminEndpoint}`, {
    headers: await getHeaders(),
    additionalOptions: {
      cache: "force-cache",
    },
  });

  if (response.status === 401) redirect("/admin/login?expired=true");
  if (response.status === 403) redirect(`/admin/unauthorised?pathname=${(await accessHeaders()).get("X-Current-Url")}`);

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
