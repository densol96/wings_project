import { Heading } from "@/components";
import Table from "@/components/shared/Table";
import { basicErrorText, parsePageableResponse, validateEventsSearchParams } from "@/utils";
import { cookies } from "next/headers";
import { EventsSearchParams, PageableResponse, SecurityEventDto, SelectOptions } from "@/types";
import FilterSelect from "@/components/ui/FilterSelect";
import NoData from "@/components/ui/NoData";
import SecurityRow from "./SecurityRow";
import Pagination from "@/components/shared/Pagination";
import SearchFilter from "@/components/shared/SearchFilter";
import { adminFetch } from "@/actions/adminFetch";

type Props = {
  searchParams: EventsSearchParams;
};

export const eventTypeSelect: SelectOptions = {
  label: "Notikuma tips",
  options: [
    { label: "Visi", value: "" },
    { label: "Reģistrācija veiksmīga", value: "NEW_USER_REGISTERED" },
    { label: "Veiksmīga pieteikšanās", value: "LOGIN_SUCCESS" },
    { label: "Neveiksmīga pieteikšanās", value: "LOGIN_FAILED" },
    { label: "Parole mainīta", value: "PASSWORD_CHANGED" },
    { label: "Piekļuve ārpus darba laika", value: "AFTER_HOURS_ACCESS" },
    { label: "Piekļuve no jaunas IP adreses", value: "ACCESS_FROM_NEW_IP" },
    { label: "Neparasts lietotāja aģents", value: "UNUSUAL_USER_AGENT" },
    { label: "Konts bloķēts", value: "ACCOUNT_LOCKED" },
    { label: "Konts atbloķēts", value: "ACCOUNT_UNLOCKED" },
    { label: "Konts aizliegts", value: "ACCOUNT_BANNED" },
    { label: "Konts atļauts", value: "ACCOUNT_UNBANNED" },
    { label: "E-pasta adrese tika nomainīta", value: "EMAIL_CHANGED" },
  ],
};

const Page = async ({ searchParams }: Props) => {
  const { page, type, q } = validateEventsSearchParams(searchParams);
  const queryParams = new URLSearchParams({ page, type, q }).toString();
  const order = await adminFetch<PageableResponse>(`security/events?${queryParams}`);
  const { content: events, totalPages } = parsePageableResponse<SecurityEventDto>(order);

  return (
    <div className="">
      <Heading size="xl">Drošības notikumu žurnāls</Heading>
      <p className="mb-5">
        Izmantojiet meklēšanas lauku un filtrus, lai atrastu konkrētus notikumus pēc lietotājvārda vai papildinformācijas, kā arī lai atlasītu notikumu tipu
        (piemēram, Pieteikšanās, Paroles maiņa, Konta bloķēšana).
      </p>
      <p className="mb-10">
        Notikumu dati tiek glabāti ar norādi uz IP adresi, pārlūkprogrammu un pieprasījuma adresi, lai nodrošinātu detalizētu uzskaiti un palīdzētu atklāt
        aizdomīgu darbību.
      </p>
      <div className="flex justify-between flex-row items-center sm:mt-0 mb-4">
        <SearchFilter name="q" className="max-w-[500px]" />
        <FilterSelect id="filterEventssBy" activeValue={type} selectDict={eventTypeSelect} filterLabel="type" />
      </div>
      {events.length ? (
        <>
          <Table
            className="text-center"
            columnNames={["Lietotājvārds", "Notikuma tips", "Datums un laiks", "IP adrese", "Lietotāja aģents", "Pieprasījuma adrese", "Papildu informācija"]}
            data={events}
            render={(event: SecurityEventDto) => <SecurityRow event={event} />}
          />
          <Pagination currentPage={+page} totalPages={totalPages} />
        </>
      ) : (
        <NoData />
      )}
    </div>
  );
};

export default Page;
