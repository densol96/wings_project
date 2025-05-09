import { CheckboxGroup } from "@/components";
import { PermissionDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";

type Props = {};

const PermissionsGroup = async ({}: Props) => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/permissions`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    },
  });

  if (!response.ok) throw new Error(basicErrorText());

  const permissions: PermissionDto[] = await response.json();
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
