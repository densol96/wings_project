"use client";

import { createContext, ReactNode, useCallback, useContext, useState } from "react";

export type ContextProps = {
  isOpen: boolean;
  close: () => void;
  open: () => void;
  toggle: () => void;
};

const SidebarContext = createContext<ContextProps | undefined>(undefined);

type ProviderProps = {
  children: ReactNode;
};

export const CategoriesSidebarProvider = ({ children }: ProviderProps) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const close = useCallback(() => setIsOpen(false), []);
  const open = useCallback(() => setIsOpen(true), []);
  const toggle = useCallback(() => setIsOpen((val) => !val), []);

  return <SidebarContext.Provider value={{ isOpen, close, open, toggle }}>{children}</SidebarContext.Provider>;
};

export const useSidebarContext = () => {
  const context = useContext(SidebarContext);
  if (!context) throw new Error("useSidebarContext must be used within a SidebarProvider");
  return context;
};
