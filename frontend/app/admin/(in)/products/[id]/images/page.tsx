import { Heading, Spinner } from "@/components";
import { IdParams, ImageDto } from "@/types";
import ManageImages from "./ManageImages";
import { Suspense } from "react";
import AddImagesForm from "./AddImagesForm";

const Page = ({ params: { id } }: IdParams) => {
  return (
    <div className="space-y-8 w-[80%]">
      <Heading size="xl">{`Produkta (#${id}) attēlu pārvaldība`}</Heading>
      <AddImagesForm productId={id} />
      <Suspense fallback={<Spinner />}>
        <ManageImages id={id} />
      </Suspense>
    </div>
  );
};

export default Page;
