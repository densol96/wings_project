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
