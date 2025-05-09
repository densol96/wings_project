import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { ModalWithWrapper } from "@/components";
import NewUser from "../../NewUser";

type Props = {};

const Page = async ({}: Props) => {
  await getUserSessionOrRedirect();
  return (
    <ModalWithWrapper>
      <NewUser className="" />
    </ModalWithWrapper>
  );
};

export default Page;
