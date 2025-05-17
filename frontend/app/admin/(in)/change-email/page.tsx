import { getCurrentUser } from "@/actions/helpers/getCurrentUser";
import ChangeEmailForm from "../ChangeEmailForm";

const Page = async () => {
  return <ChangeEmailForm className="max-w-[700px]" currentEmail={(await getCurrentUser()).email} />;
};

export default Page;
