import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { Heading } from "@/components";
import Table from "@/components/shared/Table";
import { basicErrorText, validateUsersSearchParams } from "@/utils";
import { cookies } from "next/headers";
import UserRow from "./UserRow";
import { DetailedRoleDto, SelectOptions, UserAdminDto, UsersSearchParams } from "@/types";
import Select from "@/components/ui/Select";
import FilterSelect from "@/components/ui/FilterSelect";
import AddRoleBtn from "../roles/AddRoleBtn";
import NoData from "@/components/ui/NoData";

type Props = {
  searchParams: UsersSearchParams;
};

const sortSelect: SelectOptions = {
  label: "Kārtot pēc",
  options: [
    {
      label: "Pēdējās aktivitātes laiks (no jaunākā)",
      value: "lastActivityDateTime-desc",
    },
    {
      label: "Pēdējās aktivitātes laiks (no vecākā)",
      value: "lastActivityDateTime-asc",
    },
    {
      label: "Pievienošanās datums (no jaunākā)",
      value: "joinDateTime-desc",
    },
    {
      label: "Pievienošanās datums (no vecākā)",
      value: "joinDateTime-asc",
    },
  ],
};

const filterSelect: SelectOptions = {
  label: "Lietotāja statuss",
  options: [
    {
      label: "Visi",
      value: "all",
    },
    {
      label: "Aktīvie",
      value: "active",
    },
    {
      label: "Neaktīvie",
      value: "inactive",
    },
  ],
};

const Page = async ({ searchParams }: Props) => {
  await getUserSessionOrRedirect();
  const { sort, direction, status } = validateUsersSearchParams(searchParams);
  const queryParams = new URLSearchParams({ sort, direction, status }).toString();
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/users?${queryParams}`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    },
    cache: "no-store",
  });

  if (!response.ok) throw new Error(basicErrorText());

  const roles: DetailedRoleDto[] = await response.json();

  return (
    <div className="">
      <Heading size="xl">Lomu saraksts</Heading>
      <p className="mb-5">
        Šajā skatā redzamas sistēmā definētās lomas. Katra loma sastāv no vienas vai vairākām atļaujām, kas nosaka lietotāja piekļuves tiesības konkrētām
        funkcionalitātēm vai darbībām sistēmā.
      </p>
      <p className="mb-5">
        Sistēmā ir definēti četri atļauju tipi, no kuriem dinamiskā veidā iespējams veidot jaunas lomas, pielāgojot tās atbilstoši lietotāja pienākumiem vai
        atbildībai.
      </p>
      <p className="mb-10">Zemāk esošajā tabulā redzamas visas pieejamās lomas, to nosaukumi, pievienotās atļaujas un to kopējā pieejas struktūra.</p>
      <div className="flex justify-between flex-row items-center sm:mt-0 mb-4">
        <AddRoleBtn />
        <div className="flex gap-10">
          <FilterSelect id="filterUsersBy" activeValue={status} selectDict={filterSelect} filterLabel="status" />
          <Select id="sortUsersBy" activeValue={`${sort}-${direction}`} selectDict={sortSelect} />
        </div>
      </div>
      {roles.length ? (
        <Table
          className="text-center"
          columnNames={["Lietotājvārds", "Vārds", "E-pasts", "Loma", "Statuss", "Pieteikšanās mēģinājumi", "Pēdējā aktivitāte", "Reģistrēts", "Darbības"]}
          data={roles}
          render={(role: DetailedRoleDto) => <UserRow user={user} />}
        />
      ) : (
        <NoData />
      )}
    </div>
  );
};

export default Page;
