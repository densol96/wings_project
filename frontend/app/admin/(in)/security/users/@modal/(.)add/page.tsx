import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { ModalWithWrapper } from "@/components";
import NewUser from "../../NewUser";

type Props = {};

const Page = async ({}: Props) => {
  await getUserSessionOrRedirect();
  return (
    <ModalWithWrapper>
      <NewUser className="p-20" />
    </ModalWithWrapper>
  );
};

export default Page;
