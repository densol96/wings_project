import { IdParams } from "@/types";
import OrderDetailsPage from "./OrderDetailsPage";
import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { cookies } from "next/headers";

type Props = {};

const Page = async ({ params: { id } }: IdParams) => {
  await getUserSessionOrRedirect();
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/orders/${id}`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    },
    cache: "no-store",
  });
  const data = await response.json();
  if (!response.ok) throw new Error(data.message);
  return <OrderDetailsPage order={data} />;
};

export default Page;
