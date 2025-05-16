import { IdParams, OrderFullAdminDto } from "@/types";
import OrderDetailsPage from "./OrderDetailsPage";
import { adminFetch } from "@/actions/adminFetch";

type Props = {};

const Page = async ({ params: { id } }: IdParams) => {
  const order = await adminFetch<OrderFullAdminDto>(`orders/${id}`);
  return <OrderDetailsPage order={order} />;
};

export default Page;
