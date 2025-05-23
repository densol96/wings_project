import { adminFetch } from "@/actions/helpers/adminFetch";
import { Heading, SearchFilter } from "@/components";
import { PageableResponse, ProductAdminDto, ProductsSearchParams } from "@/types";
import CategoryFilter from "./CategoryFilter";
import { parsePageableResponse } from "@/utils";
import Table from "@/components/shared/Table";
import Pagination from "@/components/shared/Pagination";
import NoData from "@/components/ui/NoData";
import ProductRow from "./ProductRow";
import Select from "@/components/ui/Select";
import AddProductBtn from "./AddProductBtn";
import { loadCategories } from "./utils";

type Props = {
  searchParams: ProductsSearchParams;
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
    {
      label: "Lielākais daudzums",
      value: "amount-desc",
    },
    {
      label: "Mazākais daudzums",
      value: "amount-asc",
    },
    {
      label: "Visvairāk pārdots",
      value: "sold-desc",
    },
    {
      label: "Vismazāk pārdots",
      value: "sold-asc",
    },
  ],
};

const Page = async ({ searchParams }: Props) => {
  const queryParams = new URLSearchParams(searchParams as unknown as { [key: string]: string }).toString();
  const [allCategories, pageableProducts] = await Promise.all([loadCategories(), adminFetch<PageableResponse>(`products?${queryParams}`)]);
  const { content: products, totalPages } = parsePageableResponse<ProductAdminDto>(pageableProducts);

  return (
    <div className="">
      <Heading size="xl">Produktu pārvaldība</Heading>

      <p className="mb-5">Izmantojiet meklēšanas lauku, lai atrastu produktus pēc LV vai EN nosaukuma.</p>
      <p className="mb-5">
        Filtri ļauj atlasīt pasūtījumus pēc kategorijas. <br />
        Pasūtījumus var kārtot pēc izveides datuma – Jaunākā vai Vecākā, kā arī pēc pēdējās rediģēšanas laika – Nesenāk vai Senāk rediģētie.
      </p>
      <div className="mb-10">
        <AddProductBtn />
      </div>
      <div className="flex justify-between gap-10 flex-row items-center sm:mt-0 mb-4">
        <SearchFilter name="q" className="max-w-[400px]" />
        <div className="flex gap-10">
          <CategoryFilter allCategories={allCategories} categoryId={searchParams.categoryId} />
          <Select activeValue={`${searchParams.sort || "createdAt"}-${searchParams.direction || "desc"}`} selectDict={sortSelectOptions} />;
        </div>
      </div>

      {products.length ? (
        <>
          <Table
            className="text-center"
            columnNames={["Nosaukums", "Daudzums", "Pārdots", "Izveidoja", "Izveidošanas datums", "Pēdējais rediģēja", "Pēdējās izmaiņas", "Darbības"]}
            data={products}
            render={(product: ProductAdminDto) => <ProductRow product={product} q={searchParams.q} />}
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
