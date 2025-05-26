import { Heading, ManageImages, Spinner } from "@/components";
import { IdParams } from "@/types";
import { Suspense } from "react";
import AddImagesForm from "../../../products/[id]/images/AddImagesForm";

const Page = ({ params: { id } }: IdParams) => {
  return (
    <div className="space-y-8 w-[80%]">
      <Heading size="xl">{`Notikuma (#${id}) attēlu pārvaldība`}</Heading>
      <AddImagesForm ownerId={id} entityType="events" />
      <Suspense fallback={<Spinner />}>
        <ManageImages id={id} entityName="events" />
      </Suspense>
    </div>
  );
};

export default Page;
