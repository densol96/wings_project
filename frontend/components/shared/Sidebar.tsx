"use client";

import { CloseBtn, LogoWithIcons } from "@/components";
import { useSidebarContext } from "@/context/SidebarContext";
import { cn } from "@/utils";
import React, { useRef } from "react";

type Props = {
  className?: string;
  children: React.ReactNode;
  breakpoint: "lg" | "md";
};

const Sidebar = ({ className, children, breakpoint }: Props) => {
  const { isOpen, close } = useSidebarContext();
  const sidebar = useRef<HTMLElement | null>(null);

  // md:-translate-x-0
  const interactiveStyles = "fixed top-0 left-0 h-full w-[300px] z-50 bg-white opacity-100 p-8 shadow-2xl transition duration-450 transform -translate-x-full";

  const closeSidebar = (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (sidebar?.current && !sidebar.current.contains(e.target as Node)) close();
  };

  return (
    <div onClick={closeSidebar}>
      <div className={cn(isOpen && `bg-gray-700 opacity-75 z-40 fixed top-0 left-0 h-full w-full ${breakpoint}:hidden`)} />
      <CloseBtn className={cn(`fixed top-10 right-10 z-50 ${breakpoint}:hidden`, !isOpen && "hidden")} />
      <aside ref={sidebar} className={cn("flex flex-col", interactiveStyles, isOpen && "translate-x-0", className)}>
        {children}
        <LogoWithIcons title={"Handmade Products"} className="mt-auto mb-20 block" />
      </aside>
    </div>
  );
};

export default Sidebar;
