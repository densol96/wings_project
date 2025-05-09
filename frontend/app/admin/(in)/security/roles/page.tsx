import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { Heading, Spinner } from "@/components";
import { DetailedRoleDto, PermissionSearchParams } from "@/types";
import { basicErrorText, validatePermissionSearchParams } from "@/utils";
import { cookies } from "next/headers";
import AddRoleBtn from "./AddRoleBtn";
import PermissionsGroup from "./PermissionsGroup";
import { Suspense } from "react";
import Table from "@/components/shared/Table";
import NoData from "@/components/ui/NoData";
import RoleRow from "./RoleRow";

type Props = {
  searchParams: PermissionSearchParams;
};

const Page = async ({ searchParams }: Props) => {
  await getUserSessionOrRedirect();

  console.log(
    `${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/roles/details?permissions=${validatePermissionSearchParams(searchParams).permissionIds.join(",")}`
  );
  const response = await fetch(
    `${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/roles/details?permissions=${validatePermissionSearchParams(searchParams).permissionIds.join(",")}`,
    {
      headers: {
        Authorization: `Bearer ${cookies().get("authToken")?.value}`,
      },
    }
  );

  if (!response.ok) throw new Error(basicErrorText());

  const roles: DetailedRoleDto[] = await response.json();

  return (
    <div>
      <Heading size="xl">Lomu saraksts</Heading>
      <p className="mb-5">
        Šajā skatā redzamas sistēmā definētās lomas. Katra loma sastāv no vienas vai vairākām atļaujām, kas nosaka lietotāja piekļuves tiesības konkrētām
        funkcionalitātēm vai darbībām sistēmā.
      </p>
      <p className="mb-10">
        Sistēmā ir definēti četri atļauju tipi, no kuriem dinamiskā veidā iespējams veidot jaunas lomas, pielāgojot tās atbilstoši lietotāja pienākumiem vai
        atbildībai.
      </p>
      <div className="flex justify-between flex-row items-center sm:mt-0 mb-4">
        <AddRoleBtn />
        <div className="flex gap-10">
          <Suspense fallback={<Spinner />}>
            <PermissionsGroup />
          </Suspense>
        </div>
      </div>
      {roles.length ? (
        <Table
          className="text-center"
          columnNames={["Lomas nosaukums", "Atļauju skaits", "Atļaujas", "Darbības"]}
          data={roles}
          render={(role: DetailedRoleDto) => <RoleRow role={role} />}
        />
      ) : (
        <NoData />
      )}
    </div>
  );
};

export default Page;
