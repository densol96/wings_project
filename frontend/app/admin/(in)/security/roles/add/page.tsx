import { Suspense } from "react";
import NewRole from "../NewRole";
import { Spinner } from "@/components";

type Props = {};

const Page = ({}: Props) => {
  return (
    <Suspense fallback={<Spinner />}>
      <NewRole />;
    </Suspense>
  );
};

export default Page;
