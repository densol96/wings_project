import Link from "next/link";
import { IoPersonAddSharp } from "react-icons/io5";

type Props = {
  href: string;
  children: React.ReactNode;
  label: string;
};

const AddBtn = ({ children, href, label }: Props) => {
  return (
    <Link href={href} className="flex gap-2 hover:bg-gray-300 p-2 rounded-md">
      {children}
      <p>{label}</p>
    </Link>
  );
};

export default AddBtn;
