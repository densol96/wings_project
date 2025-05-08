import { ModalWithWrapper } from "@/components";
import UpdatePasswordForm from "../../../UpdatePasswordForm";
import { IdParams } from "@/types";
import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";

const Page = async ({ params: { id } }: IdParams) => {
  await getUserSessionOrRedirect();
  return (
    <ModalWithWrapper>
      <UpdatePasswordForm id={id} className="bg-gray-50 p-20 shadow-custom-med" />
    </ModalWithWrapper>
  );
};

export default Page;
