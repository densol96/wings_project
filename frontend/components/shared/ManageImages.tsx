import { adminFetch } from "@/actions/helpers/adminFetch";
import { Heading } from "@/components";
import Table from "@/components/shared/Table";
import { AdminImageDto } from "@/types";
import ImageRow from "./ImageRow";
import NoData from "@/components/ui/NoData";

type Props = {
  id: number;
  entityName?: "products" | "events";
};

const ManageImages = async ({ id, entityName = "products" }: Props) => {
  const images = await adminFetch<AdminImageDto[]>(`${entityName}/${id}/images`);

  return (
    <section className="border-t-2">
      <Heading className="mt-5" as="h2" size="md">
        Esošo attēlu apskate un dzēšana
      </Heading>
      <p className="mt-5 mb-10">Jūs varat skatīt vai dzēst jau esošos produktu attēlus.</p>
      {images?.length ? (
        <Table
          className="text-center"
          tableClassname="grid-cols-[1fr_1.2fr_1fr_1fr_1fr_1fr]"
          columnNames={["Priekšskatījums", "Apraksts", "Izveidoja", "Izveidots", "Produkts", "Darbības"]}
          data={images}
          render={(product: AdminImageDto) => <ImageRow image={product} ownerId={id} entityName={entityName} />}
        />
      ) : (
        <div className="flex items-start pl-32">
          <NoData />
        </div>
      )}
    </section>
  );
};

export default ManageImages;
