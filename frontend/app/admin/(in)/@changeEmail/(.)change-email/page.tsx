import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import ChangeEmailForm from "../../ChangeEmailForm";
import { Modal, ModalWithWrapper } from "@/components/";

const Page = async () => {
  const user = await getUserSessionOrRedirect();
  return (
    <ModalWithWrapper>
      <ChangeEmailForm className="max-w-[500px]" currentEmail={user.email} />
    </ModalWithWrapper>
  );
};

export default Page;
