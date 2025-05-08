import { IdParams, IdProps } from "@/types";
import UpdatePasswordForm from "../../UpdatePasswordForm";
import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";

const Page = async ({ params: { id } }: IdParams) => {
  await getUserSessionOrRedirect();
  return <UpdatePasswordForm id={id} />;
};

export default Page;
