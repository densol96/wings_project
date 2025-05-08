import { cookies } from "next/headers";
import UserForm from "./UserForm";
import { basicErrorText } from "@/utils";

type Props = {
  className?: string;
};

const NewUser = async ({ className }: Props) => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/roles`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    },
  });

  if (!response.ok) throw new Error(basicErrorText());

  const roles = await response.json();

  return <UserForm className={className} roles={roles} />;
};

export default NewUser;
