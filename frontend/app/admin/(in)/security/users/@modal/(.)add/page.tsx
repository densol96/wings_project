import { ModalWithWrapper } from "@/components";
import NewUser from "../../NewUser";

type Props = {};

const Page = async ({}: Props) => {
  return (
    <ModalWithWrapper>
      <NewUser className="" />
    </ModalWithWrapper>
  );
};

export default Page;
