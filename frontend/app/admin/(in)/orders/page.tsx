import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { Heading, SearchFilter } from "@/components";
import Pagination from "@/components/shared/Pagination";
import Table from "@/components/shared/Table";
import FilterSelect from "@/components/ui/FilterSelect";
import NoData from "@/components/ui/NoData";
import Select from "@/components/ui/Select";
import { OrderAdminDto, OrdersSearchParams } from "@/types/sections/admin";
import { basicErrorText, parsePageableResponse, validateEventsSearchParams, validateOrdersSearchParams } from "@/utils";
import { cookies } from "next/headers";
import OrderRow from "./OrderRow";

type Props = {
  searchParams: OrdersSearchParams;
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
      value: "lastModifiedAt",
    },
    {
      label: "Senāk rediģētie",
      value: "lastModifiedAt",
    },
  ],
};

const statusFilterOptions = {
  label: "Statuss",
  options: [
    {
      label: "Visi",
      value: "",
    },
    {
      label: "Apstrādē",
      value: "IN_PROGRESS",
    },
    {
      label: "Apmaksāts",
      value: "PAID",
    },
    {
      label: "Atcelts",
      value: "CANCELLED",
    },
    {
      label: "Neizdevās",
      value: "FAILED",
    },
    {
      label: "Nosūtīts",
      value: "SHIPPED",
    },
  ],
};

const contryFilterOptions = {
  label: "Valsts",
  options: [
    {
      label: "Visi",
      value: "",
    },
    {
      label: "LV",
      value: "LV",
    },
    {
      label: "LT",
      value: "LT",
    },
    {
      label: "EE",
      value: "EE",
    },
  ],
};

const deliveryMethodOptions = {
  label: "Veids",
  options: [
    {
      label: "Visi",
      value: "",
    },
    {
      label: "Kurjers",
      value: "COURIER",
    },
    {
      label: "Saņemšana veikalā",
      value: "PICKUP",
    },
    {
      label: "Omniva pakomāts",
      value: "PARCEL_MACHINE",
    },
  ],
};

const Page = async ({ searchParams }: Props) => {
  await getUserSessionOrRedirect();
  const validatedSearchParams = validateOrdersSearchParams(searchParams) as unknown as { [key: string]: string };
  const { page, sort, direction, status, country, deliveryMethod, q } = validatedSearchParams;

  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/orders?${new URLSearchParams(validatedSearchParams).toString()}`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    },
    cache: "no-store",
  });
  if (!response.ok) throw new Error(basicErrorText());
  const { content: orders, totalPages } = parsePageableResponse<OrderAdminDto>(await response.json());

  return (
    <div className="">
      <Heading size="xl">Pasūtījumu pārvaldība</Heading>
      <p className="mb-5">
        Izmantojiet meklēšanas lauku, lai atrastu pasūtījumus pēc klienta personīgajiem datiem – vārda, uzvārda, e-pasta adreses, durvju detaļām vai piegādes
        adreses.
      </p>
      <p className="mb-10">
        Filtri ļauj atlasīt pasūtījumus pēc statusa (Apstrādē, Apmaksāts, Atcelts, Neizdevās, Nosūtīts), piegādes metodes (kurjers, pakomāts, saņemšana uz
        vietas) un valsts, uz kuru notiek piegāde (LV, LT, EE). <br />
        Pasūtījumus var kārtot pēc izveides datuma – Jaunākā vai Vecākā, kā arī pēc pēdējās rediģēšanas laika – Nesenāk vai Senāk rediģētie.
      </p>
      <div className="flex justify-between gap-10 flex-row items-center sm:mt-0 mb-4">
        <SearchFilter name="q" className="max-w-[400px]" />
        <div className="flex gap-10">
          <FilterSelect id="filterByOrderStatus" activeValue={status} selectDict={statusFilterOptions} filterLabel="status" />
          <FilterSelect id="filterByOrderCountry" activeValue={country} selectDict={contryFilterOptions} filterLabel="country" />
          <FilterSelect id="filterByOrderDeliveryMethod" activeValue={deliveryMethod} selectDict={deliveryMethodOptions} filterLabel="deliveryMethod" />
          <Select activeValue={`${sort}-${direction}`} selectDict={sortSelectOptions} />
        </div>
      </div>
      {orders.length ? (
        <>
          <Table
            className="text-center"
            columnNames={["Datums", "Statuss", "Klients", "E-pasts", "Kopējā summa", "Piegādes veids", "Piegādes adrese", "Skatīt"]}
            data={orders}
            render={(order: OrderAdminDto) => <OrderRow order={order} q={q} />}
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
