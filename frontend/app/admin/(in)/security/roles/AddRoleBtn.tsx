import AddBtn from "@/components/ui/AddBtn";
import { IoPersonAddSharp } from "react-icons/io5";

const AddRoleBtn = () => {
  return (
    <AddBtn href="/admin/security/roles/add" label="Pievienot lomu">
      <IoPersonAddSharp size={25} />
    </AddBtn>
  );
};

export default AddRoleBtn;
