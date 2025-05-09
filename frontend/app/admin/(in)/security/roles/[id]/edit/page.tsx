import { Spinner } from "@/components";
import { Suspense } from "react";
import RoleInfo from "../../RoleInfo";
import { IdParams } from "@/types";

const Page = ({ params: { id } }: IdParams) => {
  return (
    <Suspense fallback={<Spinner />}>
      <RoleInfo id={id} />;
    </Suspense>
  );
};

export default Page;
