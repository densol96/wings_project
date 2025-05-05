import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import ChangeEmailForm from "../ChangeEmailForm";

const Page = async () => {
  const user = await getUserSessionOrRedirect();
  return <ChangeEmailForm className="max-w-[700px]" currentEmail={user.email} />;
};

export default Page;
