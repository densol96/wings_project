"use client";

import { createContext, ReactNode, useCallback, useContext, useState } from "react";

export type ContextProps = {
  isOpen: boolean;
  close: () => void;
  open: () => void;
  toggle: () => void;
};

const CategoriesSidebarContext = createContext<ContextProps | undefined>(undefined);

type ProviderProps = {
  children: ReactNode;
};

export const CategoriesSidebarProvider = ({ children }: ProviderProps) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const close = useCallback(() => setIsOpen(false), []);
  const open = useCallback(() => setIsOpen(true), []);
  const toggle = useCallback(() => setIsOpen((val) => !val), []);

  return <CategoriesSidebarContext.Provider value={{ isOpen, close, open, toggle }}>{children}</CategoriesSidebarContext.Provider>;
};

export const useCategoriesSidebarContext = () => {
  const context = useContext(CategoriesSidebarContext);
  if (!context) throw new Error("useCategoriesSidebarContext must be used within a CategoriesSidebarProvider");
  return context;
};
