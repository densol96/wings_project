import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { DetailedRoleDto, FormState, IdParams, PermissionDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";
import RoleForm from "./RoleForm";

type Props = {
  id: number;
  className?: string;
};

const RoleInfo = async ({ id, className }: Props) => {
  await getUserSessionOrRedirect();

  const authToken = cookies().get("authToken")?.value;
  const baseUrl = process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED;

  const [roleRes, permissionsRes] = await Promise.all([
    fetch(`${baseUrl}/admin/roles/${id}`, {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    }),
    fetch(`${baseUrl}/admin/permissions`, {
      headers: {
        Authorization: `Bearer ${authToken}`,
      },
    }),
  ]);

  if (!roleRes.ok || !permissionsRes.ok) throw new Error(basicErrorText());

  const [role, permissions]: [DetailedRoleDto, PermissionDto[]] = await Promise.all([roleRes.json(), permissionsRes.json()]);

  return <RoleForm className={className} role={role} allPermissions={permissions} />;
};

export default RoleInfo;
