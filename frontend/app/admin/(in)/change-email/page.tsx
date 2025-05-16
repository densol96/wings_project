import { getCurrentUser } from "@/actions/helpers/getUser";
import ChangeEmailForm from "../ChangeEmailForm";

const Page = async () => {
  return <ChangeEmailForm className="max-w-[700px]" currentEmail={getCurrentUser().email} />;
};

export default Page;
