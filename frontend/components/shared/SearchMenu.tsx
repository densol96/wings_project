"use client";

import { cn } from "@/utils";
import React, { useState } from "react";
import { IoSearch } from "react-icons/io5";
import SearchForm from "./SearchForm";

type Props = {
  className?: string;
};

const SearchMenu = ({ className }: Props) => {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div className={cn("relative", className)} onMouseEnter={() => setIsHovered(true)} onMouseLeave={() => setIsHovered(false)}>
      <button
        className={cn(
          "pl-2 text-primary-bright transition-colors duration-250 text-[30px] flex items-center hover:cursor-pointer z-30",
          isHovered && "text-primary-bright-light cursor-pointer"
        )}
      >
        <IoSearch />
      </button>
      {isHovered && (
        <>
          {/* To allow mouse cursor to reach the search menu without menu collapsing onMouseLeave */}
          <div className="absolute top-0 left-0 -right-6 bottom-[-18px] z-20 cursor-pointer" />
          {/* Tail + Menu */}
          <div className="absolute bottom-[-18px] left-4 w-4 h-4 bg-gray-50 rotate-45 shadow-2xl" />
          <div className="absolute top-[115%] left-[-200px] border-2 w-[350px] bg-gray-50 shadow-custom-pro h-auto">
            <SearchForm />
          </div>
        </>
      )}
    </div>
  );
};

export default SearchMenu;
