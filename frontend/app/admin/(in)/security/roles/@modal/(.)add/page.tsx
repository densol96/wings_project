import { Suspense } from "react";
import NewRole from "../../NewRole";
import { ModalWithWrapper, Spinner } from "@/components";

const Page = () => {
  return (
    <ModalWithWrapper>
      <Suspense fallback={<Spinner />}>
        <NewRole />;
      </Suspense>
    </ModalWithWrapper>
  );
};

export default Page;
