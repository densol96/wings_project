import { DetailedRoleDto, PermissionDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies, headers } from "next/headers";
import RoleForm from "./RoleForm";

type Props = {
  id: number;
  className?: string;
};

const RoleInfo = async ({ id, className }: Props) => {
  const baseUrl = process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED;
  const options = {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      "User-Agent": headers().get("user-agent") ?? "",
      "X-Forwarded-For": headers().get("x-forwarded-for") ?? "",
    },
  };

  const [roleRes, permissionsRes] = await Promise.all([
    fetch(`${baseUrl}/admin/securirty/roles/${id}`, options),
    fetch(`${baseUrl}/admin/security/permissions`, options),
  ]);

  if (!roleRes.ok || !permissionsRes.ok) throw new Error(basicErrorText());

  const [role, permissions]: [DetailedRoleDto, PermissionDto[]] = await Promise.all([roleRes.json(), permissionsRes.json()]);

  return <RoleForm className={className} role={role} allPermissions={permissions} />;
};

export default RoleInfo;
