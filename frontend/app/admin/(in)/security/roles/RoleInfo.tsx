import { DetailedRoleDto, PermissionDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies, headers } from "next/headers";
import RoleForm from "./RoleForm";
import { getHeaders } from "@/actions/helpers/getHeaders";

type Props = {
  id: number;
  className?: string;
};

const RoleInfo = async ({ id, className }: Props) => {
  const baseUrl = process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED;
  const options = {
    headers: await getHeaders(),
  };

  const [roleRes, permissionsRes] = await Promise.all([
    fetch(`${baseUrl}/admin/security/roles/${id}`, options),
    fetch(`${baseUrl}/admin/security/permissions`, options),
  ]);

  if (!roleRes.ok || !permissionsRes.ok) throw new Error(basicErrorText());

  const [role, permissions]: [DetailedRoleDto, PermissionDto[]] = await Promise.all([roleRes.json(), permissionsRes.json()]);

  return <RoleForm className={className} role={role} allPermissions={permissions} />;
};

export default RoleInfo;
