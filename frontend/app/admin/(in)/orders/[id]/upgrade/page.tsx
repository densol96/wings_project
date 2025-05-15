import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { IdParams } from "@/types";
import { cookies } from "next/headers";
import UpgradeOrderForm from "../../UpgradeOrderForm";

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

  return <UpgradeOrderForm order={data} />;
};

export default Page;
