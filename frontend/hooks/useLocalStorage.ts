import { useEffect, useState } from "react";

const useLocalStorage = <T>(key: string, initialValue: T | null = null) => {
  const [value, setValue] = useState<T | null>(() => {
    const storedValue = localStorage.getItem(key);
    try {
      return storedValue ? JSON.parse(storedValue) : initialValue;
    } catch (err) {
      console.error("Failed to extract/parse value from localStorage:", err);
      return initialValue;
    }
  });

  const updateLocalStorage = (newValue: T) => {
    setValue(newValue);
  };

  const deleteFromLocalStorage = () => {
    setValue(null);
  };

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

  return { value, updateLocalStorage, deleteFromLocalStorage };
};

export default useLocalStorage;
