"use client";

import { useCategoriesSidebarContext } from "@/context/CategoriesSidebarContext";
import { cn } from "@/utils";
import React from "react";
import { IoOpenSharp } from "react-icons/io5";

type Props = {
  className?: string;
  title: string;
};

const ToggleCategoriesSidebar = ({ title, className }: Props) => {
  const { open } = useCategoriesSidebarContext();

  return (
    <button onClick={open} className={cn("md:hidden text-primary-bright flex flex-row gap-1 items-center underline hover:no-underline", className)}>
      <IoOpenSharp />
      <span>{title}</span>
    </button>
  );
};

export default ToggleCategoriesSidebar;
