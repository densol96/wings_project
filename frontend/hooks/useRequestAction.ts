import { useLangContext } from "@/context";
import { useEffect, useState } from "react";

type Props<R> = {
  endpoint: string;
  body?: Record<string, any>;
  headers?: Record<string, any>;
  queryParams?: Record<string, string>;
  onSuccess?: (response: R) => void;
  onError?: (e: Error) => void;
};
const useRequestAction = <R>({ endpoint, headers, body, queryParams, onSuccess, onError }: Props<R>) => {
  const [response, setResponse] = useState<R>();
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error>();

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
        throw new Error(Object.values(data)?.[0] || data);
      }
    } catch (e: unknown) {
      const errorMessage = `Bad response from ${endpoint} service: `;
      if (e instanceof Error) {
        console.error(errorMessage + e.message);
        setError(e);
        onError?.(e);
      } else {
        console.error(errorMessage, error);
        const ex = new Error(errorMessage);
        setError(ex);
        onError?.(ex);
      }
      setResponse(undefined);
    } finally {
      setIsLoading(false);
    }
  };

  return { response, isLoading, error, request };
};

export default useRequestAction;
