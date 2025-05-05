import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import ChangeEmailForm from "../../ChangeEmailForm";
import { Modal } from "@/components/shared/Modal";
import ChangePasswordForm from "../../ChangePasswordForm";

const Page = async () => {
  const user = await getUserSessionOrRedirect();
  return (
    <div className="fixed w-full h-full flex items-center justify-center backdrop-blur-sm">
      <Modal>
        <ChangeEmailForm className="max-w-[700px] p-12 shadow-custom-med" currentEmail={user.email} />
      </Modal>
    </div>
  );
};

export default Page;
