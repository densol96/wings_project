import { adminFetch } from "@/actions/helpers/adminFetch";
import { Heading, SearchFilter } from "@/components";
import { AdminEventsSearchParams, EventAdminDto, PageableResponse } from "@/types";
import { parsePageableResponse } from "@/utils";
import Table from "@/components/shared/Table";
import Pagination from "@/components/shared/Pagination";
import NoData from "@/components/ui/NoData";
import Select from "@/components/ui/Select";
import AddBtn from "@/components/ui/AddBtn";
import { IoPersonAddSharp } from "react-icons/io5";
import EventRow from "./EventRow";

type Props = {
  searchParams: AdminEventsSearchParams;
};

const sortSelectOptions = {
  label: "Kārtot pēc",
  options: [
    {
      label: "Jaunākā",
      value: "createdAt-desc",
    },
    {
      label: "Vecākā",
      value: "createdAt-asc",
    },
    {
      label: "Nesenāk rediģētie",
      value: "lastModifiedAt-desc",
    },
    {
      label: "Senāk rediģētie",
      value: "lastModifiedAt-asc",
    },
  ],
};

const Page = async ({ searchParams }: Props) => {
  const queryParams = new URLSearchParams(searchParams as unknown as { [key: string]: string }).toString();
  const { content: events, totalPages } = parsePageableResponse<EventAdminDto>(await adminFetch<PageableResponse>(`events?${queryParams}`));

  return (
    <div>
      <Heading size="xl">Notikumu pārvaldība</Heading>
      <p className="mb-5">Izmantojiet meklēšanas lauku, lai atrastu notikumus pēc nosaukuma LV vai EN valodā.</p>
      <div className="mb-10">
        <AddBtn href="/admin/news/add" label="Pievienot notikumu">
          <IoPersonAddSharp size={25} />
        </AddBtn>
      </div>

      <div className="flex justify-between gap-10 flex-row items-center sm:mt-0 mb-4">
        <SearchFilter name="q" className="max-w-[400px]" />
        <Select activeValue={`${searchParams.sort || "createdAt"}-${searchParams.direction || "desc"}`} selectDict={sortSelectOptions} />
      </div>

      {events.length ? (
        <>
          <Table
            className="text-center"
            columnNames={[
              "Nosaukums",
              "Sākuma datums",
              "Beigu datums",
              "Izveidoja",
              "Izveidošanas datums",
              "Pēdējais rediģēja",
              "Pēdējās izmaiņas",
              "Darbības",
            ]}
            data={events}
            render={(event: EventAdminDto) => <EventRow event={event} q={searchParams.q} />}
          />
          <Pagination currentPage={searchParams.page ? +searchParams.page : 1} totalPages={totalPages} />
        </>
      ) : (
        <NoData />
      )}
    </div>
  );
};

export default Page;
