import RoleForm from "./RoleForm";
import { adminFetch } from "@/actions/helpers/adminFetch";
import { PermissionDto } from "@/types";

type Props = {
  className?: string;
};

const NewRole = async ({ className }: Props) => {
  const allPermissions = await adminFetch<PermissionDto[]>("security/permissions");
  return <RoleForm className={className} allPermissions={allPermissions} />;
};

export default NewRole;
