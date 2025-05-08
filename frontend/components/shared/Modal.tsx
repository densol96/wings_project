"use client";

import { useRouter } from "next/navigation";
import { IoCloseSharp } from "react-icons/io5";

type Props = {};

const Modal = ({ children }: { children: React.ReactNode }) => {
  const router = useRouter();

  return (
    <div className="relative z-40">
      <button
        className="absolute right-4 top-4 hover:scale-110"
        onClick={() => {
          router.back();
        }}
      >
        <IoCloseSharp size={30} />
      </button>
      <div>{children}</div>
    </div>
  );
};

export default Modal;
