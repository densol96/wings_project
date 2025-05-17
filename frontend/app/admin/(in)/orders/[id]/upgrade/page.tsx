import { IdParams, OrderFullAdminDto } from "@/types";
import UpgradeOrderForm from "../../UpgradeOrderForm";
import { adminFetch } from "@/actions/helpers/adminFetch";

const Page = async ({ params: { id } }: IdParams) => {
  const order = await adminFetch<OrderFullAdminDto>(`orders/${id}`);
  return <UpgradeOrderForm order={order} />;
};

export default Page;
