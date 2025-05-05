import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { Heading } from "@/components";
import ChangePasswordForm from "../ChangePasswordForm";

const Page = async () => {
  await getUserSessionOrRedirect();
  return <ChangePasswordForm className="max-w-[600px]" />;
};

export default Page;
