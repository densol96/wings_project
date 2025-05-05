"use client";

import { StyledLink } from "@/components";
import Link from "next/link";
import { useState } from "react";
import { FaChevronDown, FaChevronUp } from "react-icons/fa";

type Props = {
  title: string;
  items: {
    name: string;
    href: string;
  }[];
};

const SidebarSection = ({ title, items }: Props) => {
  const [open, setOpen] = useState(false);

  return (
    <div className="border-b py-2">
      <button onClick={() => setOpen(!open)} className="w-full flex justify-between items-center text-left font-semibold px-4 py-2 hover:bg-gray-100 rounded">
        {title}
        {open ? <FaChevronUp size={16} /> : <FaChevronDown size={16} />}
      </button>
      {open && (
        <ul className="pl-6 mt-2 space-y-1">
          {items.map(({ name, href }, i) => (
            <li key={href + name + i} className="cursor-pointer">
              <Link href={href}>{name}</Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SidebarSection;
