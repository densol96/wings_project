import AddBtn from "@/components/ui/AddBtn";
import Link from "next/link";
import { IoPersonAddSharp } from "react-icons/io5";

const AddUserBtn = () => {
  return (
    <AddBtn href="/admin/security/users/add" label="Pievienot lietotāju">
      <IoPersonAddSharp size={25} />
    </AddBtn>
  );
};

export default AddUserBtn;
