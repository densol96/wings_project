import { adminFetch } from "@/actions/helpers/adminFetch";
import { Heading } from "@/components";
import { AdminProductCategoryDto, ProductCategoriesSearchParams } from "@/types";
import AddBtn from "@/components/ui/AddBtn";
import { IoPersonAddSharp } from "react-icons/io5";
import Select from "@/components/ui/Select";
import Table from "@/components/shared/Table";
import NoData from "@/components/ui/NoData";
import ProductRow from "../ProductRow";
import CategoryRow from "./CategoryRow";

type Props = {
  searchParams: ProductCategoriesSearchParams;
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
  console.log("queryParams ==> ", queryParams);
  const categories = await adminFetch<AdminProductCategoryDto[]>(`products/categories?${queryParams}`);

  return (
    <div className="">
      <Heading size="xl">Produktu kategoriju pārvaldība</Heading>
      <p className="mb-5">
        Produktu kategorijas var kārtot pēc izveides datuma – Jaunākā vai Vecākā, kā arī pēc pēdējās rediģēšanas laika – Nesenāk vai Senāk rediģētie.
      </p>
      <div className="mb-10">
        <AddBtn href="/admin/products/categories/add" label="Pievienot kategoriju">
          <IoPersonAddSharp size={25} />
        </AddBtn>
      </div>

      {categories.length ? (
        <>
          <div className="flex justify-between gap-10 flex-row items-center sm:mt-0 mb-4">
            <Select activeValue={`${searchParams.sort || "createdAt"}-${searchParams.direction || "desc"}`} selectDict={sortSelectOptions} />;
          </div>
          <Table
            className="text-center"
            columnNames={["Nosaukums", "Produktu skaits", "Izveidoja", "Izveidošanas datums", "Pēdējais rediģēja", "Pēdējās izmaiņas", "Darbības"]}
            data={categories}
            render={(category: AdminProductCategoryDto) => <CategoryRow category={category} />}
          />
        </>
      ) : (
        <NoData />
      )}
    </div>
  );
};

export default Page;
