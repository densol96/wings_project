import { Modal } from "@/components/shared";
import ChangePasswordForm from "../../ChangePasswordForm";

const Page = async () => {
  return (
    <div className="fixed w-full h-full flex items-center justify-center backdrop-blur-sm">
      <Modal>
        <ChangePasswordForm className="max-w-[500px]" />
      </Modal>
    </div>
  );
};

export default Page;
