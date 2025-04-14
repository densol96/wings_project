import { useLangContext } from "@/context";
import { useState } from "react";

type Props = {
  endpoint: string;
  body?: Record<string, any>;
  headers?: Record<string, any>;
  queryParams?: Record<string, string>;
};
const useRequestAction = <R>({ endpoint, headers, body, queryParams }: Props) => {
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
      console.log(url);
      queryParams && Object.keys({ ...queryParams }).forEach((key) => url.searchParams.append(key, queryParams[key]));
      const response = await fetch(url, options);
      const data = await response.json();
      if (response.ok) setResponse(data as R);
      else throw new Error(Object.values(data)?.[0] || data);
    } catch (e: unknown) {
      const errorMessage = `Bad response from ${endpoint} service: `;
      if (e instanceof Error) {
        console.error(errorMessage + e.message);
        setError(e);
      } else {
        console.error(errorMessage, error);
        setError(new Error(errorMessage));
      }
      setResponse(undefined);
    } finally {
      setIsLoading(false);
    }
  };

  return { response, isLoading, error, request };
};

export default useRequestAction;
