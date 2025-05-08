import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { Modal } from "@/components/shared";
import ChangePasswordForm from "../../ChangePasswordForm";

const Page = async () => {
  const user = await getUserSessionOrRedirect();
  return (
    <div className="fixed w-full h-full flex items-center justify-center backdrop-blur-sm">
      <Modal>
        <ChangePasswordForm className="max-w-[700px] p-12 shadow-custom-med" />
      </Modal>
    </div>
  );
};

export default Page;
