"use client";

import { cn } from "@/utils";
import Link from "next/link";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import React, { useCallback } from "react";
import { FaFastBackward, FaStepBackward, FaStepForward, FaFastForward } from "react-icons/fa";

type Props = {
  className?: string;
  children?: React.ReactNode;
  currentPage: number;
  totalPages: number;
  maxVisible?: number;
  temp: number;
};

const Pagination = ({ className, children, currentPage, totalPages, maxVisible = 5 }: Props) => {
  const getPages = useCallback(() => {
    let start = Math.max(1, currentPage - Math.floor(maxVisible / 2));
    let end = Math.min(totalPages, start + maxVisible - 1);
    if (end - start + 1 < maxVisible) {
      start = Math.max(1, end - maxVisible + 1);
    }
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  }, [currentPage, maxVisible]);

  currentPage = currentPage > 1 ? currentPage : 1;

  const searchParams = useSearchParams();
  const router = useRouter();
  const pathname = usePathname();

  const setPage = useCallback(
    (newPage: number) => {
      const params = new URLSearchParams(searchParams);
      params.set("page", `${newPage}`);
      router.replace(`${pathname}?${params.toString()}`);
    },
    [searchParams, pathname]
  );

  return (
    totalPages > 1 && (
      <div className="flex justify-center mt-16 gap-2 text-primary-bright">
        {currentPage - 1 > 1 && (
          <button onClick={() => setPage(1)}>
            <FaFastBackward />
          </button>
        )}
        {currentPage > 1 && (
          <button onClick={() => setPage(currentPage - 1)}>
            <FaStepBackward />
          </button>
        )}
        {getPages().map((page) => {
          return (
            <button
              key={"paination_" + page}
              disabled={currentPage === page}
              onClick={() => setPage(page)}
              className={cn(
                "border-2 border-primary-bright rounded-full w-10 h-10 hover:bg-primary-bright hover:text-gray-50 transition duration-200",
                page === currentPage && "bg-primary-bright text-gray-50"
              )}
            >
              {page}
            </button>
          );
        })}
        {currentPage < totalPages && (
          <button onClick={() => setPage(currentPage + 1)}>
            <FaStepForward />
          </button>
        )}
        {totalPages - currentPage > 1 && (
          <button onClick={() => setPage(totalPages)}>
            <FaFastForward />
          </button>
        )}
      </div>
    )
  );
};

export default Pagination;
