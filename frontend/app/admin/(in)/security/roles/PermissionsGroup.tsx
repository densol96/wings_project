import { adminFetch } from "@/actions/adminFetch";
import { CheckboxGroup } from "@/components";
import { PermissionDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";

type Props = {};

const PermissionsGroup = async ({}: Props) => {
  const permissions = await adminFetch<PermissionDto[]>(`security/permissions`);
  return (
    <div className="flex gap-2">
      <p className="font-medium">Filtrēt lomas pēc atļaujām: </p>
      <div className="flex gap-2">
        <CheckboxGroup name="permissions" options={permissions.map((p) => ({ value: p.id, label: p.label }))} />
      </div>
    </div>
  );
};

export default PermissionsGroup;
