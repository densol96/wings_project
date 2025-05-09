"use client";

import { useDebounceEffect } from "@/hooks";
import { useState } from "react";
import { Input } from "../ui";
import { usePathname, useRouter, useSearchParams } from "next/navigation";

type Props = {
  name: string;
  className?: string;
};

const SearchFilter = ({ name, className }: Props) => {
  const [query, setQuery] = useState("");
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();

  const onChange = () => {
    const params = new URLSearchParams(searchParams);
    params.set(name, query);
    params.set("page", "1");
    router.replace(`${pathname}?${params.toString()}`, { scroll: false });
  };

  useDebounceEffect(
    onChange,
    300, // ms
    [query]
  );

  return <Input className={className} name="q" placeholder="Lietotājvārds vai Papildu informācija" value={query} onChange={(e) => setQuery(e.target.value)} />;
};

export default SearchFilter;
