import { IdParams } from "@/types";
import UserForm from "../../../UserForm";
import UserInfo from "../../../UserInfo";
import { ModalWithWrapper } from "@/components";

const Page = ({ params: { id } }: IdParams) => {
  return (
    <ModalWithWrapper>
      <UserInfo className="w-[800px]" id={id} />
    </ModalWithWrapper>
  );
};

export default Page;
