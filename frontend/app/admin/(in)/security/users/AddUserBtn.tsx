import Link from "next/link";
import { IoPersonAddSharp } from "react-icons/io5";

const AddUserBtn = () => {
  return (
    <Link href="/admin/security/users/add" className="flex gap-2 hover:bg-gray-300">
      <IoPersonAddSharp size={25} />
      <p>Pievienot lietotāju</p>
    </Link>
  );
};

export default AddUserBtn;
