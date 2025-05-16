import { RoleDto, UserDetailsDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies, headers } from "next/headers";
import UserForm from "./UserForm";

type Props = {
  id: number;
  className?: string;
};

const UserInfo = async ({ id, className }: Props) => {
  const baseUrl = process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED;
  const options = {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      "User-Agent": headers().get("user-agent") ?? "",
      "X-Forwarded-For": headers().get("x-forwarded-for") ?? "",
    },
  };
  const [userRes, rolesRes] = await Promise.all([fetch(`${baseUrl}/admin/security/users/${id}`, options), fetch(`${baseUrl}/admin/security/roles`, options)]);

  if (!userRes.ok || !rolesRes.ok) throw new Error(basicErrorText());

  const [user, roles]: [UserDetailsDto, RoleDto[]] = await Promise.all([userRes.json(), rolesRes.json()]);

  return <UserForm className={className} id={id} user={user} roles={roles} />;
};

export default UserInfo;
