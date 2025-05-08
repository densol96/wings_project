import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { Heading } from "@/components";
import Table from "@/components/shared/Table";
import { basicErrorText, formatDate, validateUsersSearchParams } from "@/utils";
import { cookies } from "next/headers";
import UserRow from "./UserRow";
import { SelectOptions, UserAdminDto, UsersSearchParams } from "@/types";
import Select from "@/components/ui/Select";
import FilterSelect from "@/components/ui/FilterSelect";
import NoData from "@/components/ui/NoData";
import AddUserBtn from "./AddUserBtn";

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

  const users: UserAdminDto[] = await response.json();

  return (
    <div className="">
      <Heading size="xl">Darbinieku saraksts</Heading>
      <p className="mb-5">
        Izmantojiet filtrus, lai atlasītu lietotājus pēc statusa (Aktīvs, Bloķēts, Aizliegts) un kārtošanas iespējas, lai sakārtotu sarakstu pēc pēdējās
        aktivitātes datuma (Pēdējā aktivitāte) vai pievienošanās datuma (Reģistrēts) augošā vai dilstošā secībā.
      </p>
      <p className="mb-5">Lietotāja aktivitāte tiek reģistrēta ne biežāk kā reizi 15 minūtēs, lai samazinātu sistēmas noslodzi.</p>
      <p className="mb-10">
        Laukā “Pieteikšanās mēģinājumi” tiek parādīts neveiksmīgo pieslēgšanās mēģinājumu skaits. Ja šis skaits sasniedz 5, lietotāja konts tiek automātiski
        bloķēts drošības apsvērumu dēļ.
      </p>
      <div className="flex justify-between flex-row items-center sm:mt-0 mb-4">
        <AddUserBtn />
        <div className="flex gap-10">
          <FilterSelect id="filterUsersBy" activeValue={status} selectDict={filterSelect} filterLabel="status" />
          <Select id="sortUsersBy" activeValue={`${sort}-${direction}`} selectDict={sortSelect} />
        </div>
      </div>
      {users.length ? (
        <Table
          className="text-center"
          columnNames={["Lietotājvārds", "Vārds", "E-pasts", "Loma", "Statuss", "Pieteikšanās mēģinājumi", "Pēdējā aktivitāte", "Reģistrēts", "Darbības"]}
          data={users}
          render={(user: UserAdminDto) => <UserRow user={user} />}
        />
      ) : (
        <NoData />
      )}
    </div>
  );
};

export default Page;
