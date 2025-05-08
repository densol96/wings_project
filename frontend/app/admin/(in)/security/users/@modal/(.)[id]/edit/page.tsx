import { IdParams } from "@/types";
import UserForm from "../../../UserForm";
import UserInfo from "../../../UserInfo";
import { ModalWithWrapper } from "@/components";

const Page = ({ params: { id } }: IdParams) => {
  return (
    <ModalWithWrapper>
      <UserInfo className="bg-gray-50 p-20 shadow-custom-med max-w-[700px]" id={id} />
    </ModalWithWrapper>
  );
};

export default Page;
