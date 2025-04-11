"use client";

import { Locale } from "@/types";
import { createContext, ReactNode, useCallback, useContext, useState } from "react";

export type LangContextProps = {
  lang: Locale;
};

const LangContext = createContext<LangContextProps | undefined>(undefined);

type ProviderProps = {
  children: ReactNode;
  langValueFromServer: Locale;
};

export const LangProvider = ({ children, langValueFromServer }: ProviderProps) => {
  console.log("CLIENT");
  const [lang] = useState<Locale>(langValueFromServer);
  return <LangContext.Provider value={{ lang }}>{children}</LangContext.Provider>;
};

export const useLangContext = () => {
  const context = useContext(LangContext);
  if (!context) throw new Error("useLangContext must be used within a LangProvider");
  return context;
};
