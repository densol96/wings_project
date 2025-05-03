import { UserSessionInfoDto } from "@/types";
import { notFound } from "next/navigation";

export const fetcher = async function <T>(url: string, showNotFoundFor: number[] = [404]): Promise<T> {
  const response = await fetch(url);
  if (showNotFoundFor.includes(response.status)) notFound();
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || "Error while fetching from: " + url); // could be used for logging, dict-translation can happen in an error boundary
  }
  return data as T;
};

export const getUserSession = async (token: string): Promise<UserSessionInfoDto | null> => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/auth/user-data`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    cache: "no-store",
  });
  if (!response.ok) return null;
  return await response.json();
};
