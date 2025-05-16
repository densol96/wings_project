import { getCurrentUser } from "@/actions/helpers/getUser";
import ChangeEmailForm from "../../ChangeEmailForm";
import { ModalWithWrapper } from "@/components/";

const Page = async () => {
  return (
    <ModalWithWrapper>
      <ChangeEmailForm className="max-w-[500px]" currentEmail={getCurrentUser().email} />
    </ModalWithWrapper>
  );
};

export default Page;
