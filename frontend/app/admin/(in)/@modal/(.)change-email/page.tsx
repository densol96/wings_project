import { getCurrentUser } from "@/actions/helpers/getCurrentUser";
import ChangeEmailForm from "../../ChangeEmailForm";
import { ModalWithWrapper } from "@/components/";

const Page = async () => {
  return (
    <ModalWithWrapper>
      <ChangeEmailForm className="max-w-[500px]" currentEmail={(await getCurrentUser()).email} />
    </ModalWithWrapper>
  );
};

export default Page;
