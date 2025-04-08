import { useEffect } from "react";

const useDebounceEffect = (callback: () => void, delay: number, deps: any[]) => {
  useEffect(() => {
    const handler = setTimeout(() => {
      callback();
    }, delay);

    return () => clearTimeout(handler);
  }, deps);
};

export default useDebounceEffect;
