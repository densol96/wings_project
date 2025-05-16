import { IdParams } from "@/types";
import UpdatePasswordForm from "../../UpdatePasswordForm";

const Page = async ({ params: { id } }: IdParams) => {
  return <UpdatePasswordForm id={id} />;
};

export default Page;
