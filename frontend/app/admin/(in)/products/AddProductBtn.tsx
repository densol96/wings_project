import AddBtn from "@/components/ui/AddBtn";
import { IoPersonAddSharp } from "react-icons/io5";

type Props = {
  className?: string;
};

const AddProductBtn = ({ className }: Props) => {
  return (
    <AddBtn href="/admin/products/add" label="Pievienot produktu" className={className}>
      <IoPersonAddSharp size={25} />
    </AddBtn>
  );
};

export default AddProductBtn;
