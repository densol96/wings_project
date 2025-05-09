"use client";

import { createUser } from "@/actions/users/createUser";
import { updateUser } from "@/actions/users/updateUser";
import { Button, Form, FormField, Heading, MultiSelect } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { FormState, RoleDto, UserDetailsDto } from "@/types";
import { cn, normalizeError } from "@/utils";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useFormState } from "react-dom";
import toast from "react-hot-toast";

type Props = {
  id?: number;
  user?: UserDetailsDto;
  roles: RoleDto[];
  className?: string;
};

const UserForm = ({ id, user, roles, className }: Props) => {
  const roleOptions = roles.map((role) => ({
    value: role.id,
    label: role.name,
  }));

  const [state, formAction] = useFormState<FormState, FormData>(id && user ? updateUser : createUser, null);
  const router = useRouter();

  useEffect(() => {
    if (state?.error?.message) {
      toast.error(state.error.message);
    } else if (state?.success?.message) {
      toast.success(state.success.message);
    }
  }, [state?.error, state?.success]);

  const [selectedRolesIds, setSelectedRolesIds] = useState(user?.roles || []);

  return (
    <Form action={formAction} cols={2} className={cn("md:gap-6 grid-cols-[1fr] gap-2 max-w-[800px] bg-gray-50", className)}>
      <Heading className="col-span-2" size="xl">
        {id && user ? "Lietotāja iestatījumi" : "Pievienot jaunu lietotāju"}
      </Heading>
      <input type="hidden" name="id" value={id} />
      <FormField label="Lietotājvārds" name="username" error={normalizeError(state?.errors?.username)} defaultValue={user?.username} required />
      <FormField label="E-pasts" name="email" type="email" error={normalizeError(state?.errors?.email)} defaultValue={user?.email} required />
      <FormField label="Vārds" name="firstName" error={normalizeError(state?.errors?.firstName)} defaultValue={user?.firstName} required />
      <FormField label="Uzvārds" name="lastName" error={normalizeError(state?.errors?.lastName)} defaultValue={user?.lastName} required />
      {id && user && (
        <>
          <FormField
            type="checkbox"
            label="Konts ir bloķēts"
            name="accountLocked"
            error={normalizeError(state?.errors?.accountLocked)}
            defaultChecked={user?.accountLocked || false}
            required
          />
          <FormField
            type="checkbox"
            label="Konts ir aizliegts (banned)"
            name="accountBanned"
            error={normalizeError(state?.errors?.accountBanned)}
            defaultChecked={user?.accountBanned || false}
            required
          />
        </>
      )}
      <MultiSelect
        className="col-span-2"
        selectedValues={user?.roles || []}
        name="roles_selector"
        label="Lomas"
        placeholder="Izvēlieties lomas"
        options={roleOptions}
        setSelected={setSelectedRolesIds}
      />
      {selectedRolesIds.map((id) => (
        <input key={id} type="hidden" name="roles" value={id} />
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

export default UserForm;
