"use client";

import createRole from "@/actions/roles/createRole";
import updateRole from "@/actions/roles/updateRole";
import { Button, Form, FormField, Heading, MultiSelect } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { DetailedRoleDto, FormState, PermissionDto } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  role?: DetailedRoleDto;
  className?: string;
  allPermissions: PermissionDto[];
};

const RoleForm = ({ role, className, allPermissions }: Props) => {
  const [state, formAction] = useFormState<FormState, FormData>(role ? updateRole : createRole, null);
  const router = useRouter();

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
    }
  }, [state?.error, state?.success]);

  const [selectedPermissionIds, setSelectedPermissionIds] = useState(role?.permissions?.map((p) => p.id) || []);

  const permissionsOptions = allPermissions.map((p) => ({ value: p.id, label: p.label }));

  return (
    <Form action={formAction} cols={2} className={cn("md:gap-6 grid-cols-[1fr] gap-2 max-w-[800px]", className)}>
      <Heading className="col-span-2" size="xl">
        {role ? "Lomas iestatījumi" : "Pievienot jaunu lomu"}
      </Heading>
      <input type="hidden" name="id" value={role?.id} />
      <FormField label="Lomas nosaukums" name="name" error={normalizeError(state?.errors?.name)} defaultValue={role?.name} required className="col-span-2" />
      <MultiSelect
        className="col-span-2"
        selectedValues={selectedPermissionIds}
        name="permissions_selector"
        label="Atļaujas"
        placeholder="Izvēlieties atļaujas"
        options={permissionsOptions}
        setSelected={setSelectedPermissionIds}
      />
      {selectedPermissionIds.map((id) => (
        <input key={id} type="hidden" name="permissions" value={id} />
      ))}
      <div className="col-span-2 flex flex-col items-center gap-10">
        <SubmitButton className="w-[300px]" color="green">
          Apstiprināt
        </SubmitButton>
        <Button onClick={() => router.back()}>Atgriezties</Button>
      </div>
    </Form>
  );
};

export default RoleForm;
