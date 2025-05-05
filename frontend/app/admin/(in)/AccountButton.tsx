"use client";
import Link from "next/link";
import { useState } from "react";
import { MdAccountCircle, MdEmail } from "react-icons/md";
import { RiLockPasswordFill } from "react-icons/ri";

type Props = {};

const AccountButton = ({}: Props) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="relative" onMouseEnter={() => setIsOpen(true)} onMouseLeave={() => setIsOpen(false)}>
      <MdAccountCircle size={30} />
      {isOpen && (
        <>
          <div className="w-40 h-20 -top-2 right-0 absolute" />
          <ul className="absolute right-0 w-44 bg-white border rounded shadow z-10 top-full mt-2">
            <li className=" hover:bg-gray-300 cursor-pointer">
              <Link className="w-full h-full px-4 py-2 flex items-center gap-2" href="/admin/change-email">
                <MdEmail />
                <span>E-pasta maiņa</span>
              </Link>
            </li>
            <li className="hover:bg-gray-300 cursor-pointer">
              <Link className="w-full h-full px-4 py-2 flex items-center gap-2" href="/admin/change-password">
                <RiLockPasswordFill />
                <span>Paroles maiņa</span>
              </Link>
            </li>
          </ul>
        </>
      )}
    </div>
  );
};

export default AccountButton;
