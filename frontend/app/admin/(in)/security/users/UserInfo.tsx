import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { RoleDto, UserAdminDto, UserDetailsDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";
import UserForm from "./UserForm";

type Props = {
  id: number;
  className?: string;
};

const UserInfo = async ({ id, className }: Props) => {
  await getUserSessionOrRedirect();

  const authToken = cookies().get("authToken")?.value;
  const baseUrl = process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED;

  const [userRes, rolesRes] = await Promise.all([
    fetch(`${baseUrl}/admin/users/${id}`, {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    }),
    fetch(`${baseUrl}/admin/roles`, {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    }),
  ]);

  if (!userRes.ok || !rolesRes.ok) throw new Error(basicErrorText());

  const [user, roles]: [UserDetailsDto, RoleDto[]] = await Promise.all([userRes.json(), rolesRes.json()]);

  return <UserForm className={className} id={id} user={user} roles={roles} />;
};

export default UserInfo;
