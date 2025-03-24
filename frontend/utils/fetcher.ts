import { notFound, redirect } from "next/navigation";

const fetcher = async function <T>(url: string, showNotFoundFor: number[] = [404]): Promise<T> {
  const response = await fetch(url);
  const data = await response.json();
  if (response.status === 400 || response.status === 404) notFound();
  if (!response.ok) {
    throw new Error(data.message || "Error while fetching from: " + url); // could be used for logging, dict-translation can happen in an erorr boundary
  }
  return data as T;
};

export default fetcher;
