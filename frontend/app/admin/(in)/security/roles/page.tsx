import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import { DetailedRoleDto } from "@/types";
import { basicErrorText } from "@/utils";
import { cookies } from "next/headers";

type Props = {};

const Page = async ({}: Props) => {
  await getUserSessionOrRedirect();
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/admin/roles/details`, {
    headers: {
      Authorization: `Bearer ${cookies().get("authToken")?.value}`,
    },
  });

  if (!response.ok) throw new Error(basicErrorText());

  const users: DetailedRoleDto[] = await response.json();
};

export default Page;
