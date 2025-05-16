import { ModalWithWrapper } from "@/components";
import UpdatePasswordForm from "../../../UpdatePasswordForm";
import { IdParams } from "@/types";

const Page = async ({ params: { id } }: IdParams) => {
  return (
    <ModalWithWrapper>
      <UpdatePasswordForm id={id} />
    </ModalWithWrapper>
  );
};

export default Page;
