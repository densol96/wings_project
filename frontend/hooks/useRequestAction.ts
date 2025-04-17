import { useLangContext } from "@/context";
import { useEffect, useState } from "react";

type Props<R, E> = {
  endpoint: string;
  body?: Record<string, any>;
  headers?: Record<string, any>;
  queryParams?: Record<string, string>;
  onSuccess?: (response: R) => void;
  onError?: (e: E) => void;
  onFallbackError?: (e: Error) => void;
};

const errorFallback = {
  lv: "Šis pakalpojums pašlaik nav pieejams.",
  en: "This service is currently unavailable.",
};

const useRequestAction = <R, E>({ endpoint, headers, body, queryParams, onSuccess, onError, onFallbackError }: Props<R, E>) => {
  const [response, setResponse] = useState<R>();
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<E | Error>();

  const { lang } = useLangContext();

  const options: RequestInit = {
    method: body ? "POST" : "GET",
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    ...(body && { body: JSON.stringify(body) }),
  };

  const request = async () => {
    try {
      setIsLoading(true);
      setError(undefined);
      const url = new URL(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/${endpoint}`);
      url.searchParams.append("lang", lang);
      queryParams &&
        Object.entries(queryParams).forEach(([key, value]) => {
          url.searchParams.append(key, value);
        });
      const response = await fetch(url, options);
      const data = await response.json();

      if (response.ok) {
        const result = data as R;
        setResponse(result);
        onSuccess?.(result);
      } else {
        throw data as E;
      }
    } catch (e: unknown) {
      if (e instanceof Error) {
        console.error(`Unexpected error in useRequestAction for ${endpoint}`, e);
        const fallbackError = new Error(errorFallback[lang]);
        setError(fallbackError);
        onFallbackError?.(fallbackError);
        return;
      }
      const err = e as E;
      setError(err);
      onError?.(err);
      setResponse(undefined);
    } finally {
      setIsLoading(false);
    }
  };

  return { response, isLoading, error, request };
};

export default useRequestAction;
