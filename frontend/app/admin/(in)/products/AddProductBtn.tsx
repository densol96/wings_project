import AddBtn from "@/components/ui/AddBtn";
import { IoPersonAddSharp } from "react-icons/io5";

type Props = {};

const AddProductBtn = () => {
  return (
    <AddBtn href="/admin/products/add" label="Pievienot produktu">
      <IoPersonAddSharp size={25} />
    </AddBtn>
  );
};

export default AddProductBtn;
