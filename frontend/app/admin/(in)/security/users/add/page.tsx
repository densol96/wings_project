import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";
import UserForm from "../UserForm";
import NewUser from "../NewUser";

type Props = {};

const Page = async ({}: Props) => {
  await getUserSessionOrRedirect();
  return <NewUser />;
};

export default Page;
