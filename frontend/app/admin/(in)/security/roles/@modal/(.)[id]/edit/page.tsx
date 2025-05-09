import { ModalWithWrapper, Spinner } from "@/components";
import { Suspense } from "react";
import RoleInfo from "../../../RoleInfo";
import { IdParams } from "@/types";

const Page = ({ params: { id } }: IdParams) => {
  return (
    <ModalWithWrapper>
      <Suspense fallback={<Spinner />}>
        <RoleInfo id={id} />;
      </Suspense>
    </ModalWithWrapper>
  );
};

export default Page;
