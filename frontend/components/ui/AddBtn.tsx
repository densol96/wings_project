import { cn } from "@/utils";
import Link from "next/link";
import { IoPersonAddSharp } from "react-icons/io5";

type Props = {
  href: string;
  children: React.ReactNode;
  label: string;
  className?: string;
};

const AddBtn = ({ children, href, label, className }: Props) => {
  return (
    <Link href={href} className={cn("flex gap-2 hover:bg-gray-300 py-2 px-4 rounded-md w-fit", className)}>
      {children}
      <p>{label}</p>
    </Link>
  );
};

export default AddBtn;
