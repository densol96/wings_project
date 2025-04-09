"use client";

import { Locale } from "@/types";
import { createContext, ReactNode, useCallback, useContext, useState } from "react";

export type ContextProps = {
  lang: Locale;
};

const LangContext = createContext<ContextProps | undefined>(undefined);

type ProviderProps = {
  children: ReactNode;
  langValueFromServer: Locale;
};

export const LangProvider = ({ children, langValueFromServer }: ProviderProps) => {
  const [lang] = useState<Locale>(langValueFromServer);
  console.log("LangProvider ==> ", lang);
  return <LangContext.Provider value={{ lang }}>{children}</LangContext.Provider>;
};

export const useLangContext = () => {
  const context = useContext(LangContext);
  if (!context) throw new Error("useLangContext must be used within a LangProvider");
  return context;
};
