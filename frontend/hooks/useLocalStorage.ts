import { useEffect, useState } from "react";
import useEffectOnce from "./useEffectOnce";

const useLocalStorage = <T>(key: string, initialValue: T | null = null) => {
  const [value, setValue] = useState<T | null>(initialValue);

  useEffectOnce(() => {
    const storedValue = localStorage.getItem(key);
    try {
      if (storedValue) {
        setValue(JSON.parse(storedValue));
      }
    } catch (err) {
      console.error("Failed to extract/parse value from localStorage:", err);
    }
  });

  useEffect(() => {
    try {
      if (value !== null) {
        localStorage.setItem(key, JSON.stringify(value));
      } else {
        localStorage.removeItem(key);
      }
    } catch (err) {
      console.error("Failed to update localStorage:", err);
    }
  }, [value]);

  // const updateLocalStorage = (newValue: T) => {
  //   setValue(newValue);
  // };

  const deleteFromLocalStorage = () => {
    setValue(null);
  };

  return { value, updateLocalStorage: setValue, deleteFromLocalStorage };
};

export default useLocalStorage;
