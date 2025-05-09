import { ModalWithWrapper } from "@/components";
import UpdatePasswordForm from "../../../UpdatePasswordForm";
import { IdParams } from "@/types";
import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";

const Page = async ({ params: { id } }: IdParams) => {
  await getUserSessionOrRedirect();
  return (
    <ModalWithWrapper>
      <UpdatePasswordForm id={id} />
    </ModalWithWrapper>
  );
};

export default Page;
