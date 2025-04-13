import { useEffect, useState } from "react";

type LoadStatus<T> = {
  data: T | null;
  isLoading: boolean;
  error: Error | null;
};

export function useLoadData<T>(url: string, deps: any[] = [], defaultValue: T | null = null): LoadStatus<T> {
  const [data, setData] = useState<T | null>(defaultValue);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const controller = new AbortController();
    const signal = controller.signal;

    async function load() {
      setIsLoading(true);
      setError(null);
      try {
        const res = await fetch(url, { signal });
        if (!res.ok) throw new Error(`Request failed: ${res.status}`);
        const json = await res.json();
        setData(json);
      } catch (err) {
        if ((err as any).name !== "AbortError") {
          setError(err as Error);
        }
      } finally {
        setIsLoading(false);
      }
    }

    load();
    return () => controller.abort();
  }, deps);

  return { data, isLoading, error };
}
