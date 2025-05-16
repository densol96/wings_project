import { FormState, HttpMethod, Locale, UserSessionInfoDto } from "@/types";
import { notFound, redirect } from "next/navigation";
import toast from "react-hot-toast";
import { basicErrorText, displayError, getFullUrl, normalizeError } from "./parse";
import { cookies, headers } from "next/headers";
import { revalidatePath } from "next/cache";

// for SC => error boundary will ahndle the network error
export const fetcher = async function <T>(url: string, showNotFoundFor: number[] = [404]): Promise<T> {
  const response = await fetch(url);
  if (showNotFoundFor.includes(response.status)) notFound();
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || "Error while fetching from: " + url); // could be used for logging, dict-translation can happen in an error boundary
  }
  return data as T;
};

export const fetchWithSetup = async (
  url: string,
  {
    body,
    method,
    headers,
    additionalOptions,
  }: {
    body?: Record<string, any>;
    method?: HttpMethod;
    headers?: Record<string, any>;
    additionalOptions?: Record<string, any>;
  },
  lang: Locale = "lv"
): Promise<Response> => {
  const options = {
    method: method || "GET",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    ...(body && { body: JSON.stringify(body) }),
    ...(method && method !== "GET" && { cache: "no-store" as RequestCache }),
    ...additionalOptions,
  };
  const fullUrl = url.startsWith("http") ? url : `${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/${url}`;
  console.log("FULL URL => ", fullUrl);
  console.log(options);
  return await fetch(fullUrl, options);
};

// For trying to communicate from the CC
export const handleFormSubmission = async (
  endpoint: string,
  {
    body,
    method = "POST",
    headers,
    additionalOptions,
  }: {
    body?: Record<string, any>;
    method?: HttpMethod;
    headers?: HeadersInit;
    additionalOptions?: RequestInit;
  },
  onSuccess?: (data: { message: string }) => void,
  onError?: (response: Response, error: { message: string }) => void,
  onGoneError?: (error: { message: string }) => void,
  onNetworkError?: (error: any) => void
) => {
  try {
    const response = await fetchWithSetup(getFullUrl(endpoint), {
      body,
      additionalOptions: {
        cache: "no-store",
        ...additionalOptions,
      },
      method,
      headers,
    });

    const data = await response.json();
    if (response.ok) {
      toast.success(data.message);
      onSuccess?.(data);
    } else {
      onError?.(response, data);

      if (response.status === 400) {
        return data as Record<string, string>; // field errors
      } else {
        toast.error(normalizeError(data)!);
        if (response.status === 410) {
          onGoneError?.(data);
        }
      }
    }
  } catch (e: any) {
    // network error
    toast.error(basicErrorText());
    onNetworkError?.(e);
  }
};

export const encryptUser = (baseUrl: string, user: UserSessionInfoDto): Promise<string> => {
  return fetch(`${baseUrl}/api/encrypt-user`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ user }),
  })
    .then((res) => res.json())
    .then((data) => data.encryptedUser);
};
