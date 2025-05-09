import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";
import RoleForm from "./RoleForm";

type Props = {
  className?: string;
};

const NewRole = async ({ className }: Props) => {
  await getUserSessionOrRedirect();

  const authToken = cookies().get("authToken")?.value;
  const baseUrl = process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED;

  const res = await fetch(`${baseUrl}/admin/permissions`, {
    headers: {
      Authorization: `Bearer ${authToken}`,
    },
  });

  if (!res.ok) throw new Error(basicErrorText());

  const allPermissions = await res.json();
  return <RoleForm className={className} allPermissions={allPermissions} />;
};

export default NewRole;
