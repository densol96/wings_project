import { Heading, StyledLink } from "@/components";
import { TokenProps } from "@/types";
import React, { cache } from "react";
import { FaHome } from "react-icons/fa";

const Page = async ({ params: { token } }: TokenProps) => {
  const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/auth/request-account-unlock/${token}`, {
    method: "POST",
    cache: "no-store",
  });
  const data = (await response.json()) as { message: string };
  return (
    <div className="max-w-[500px] text-center">
      <Heading className="mb-10" size="lg">
        {response.ok ? "Apstiprināšanas kods nosūtīts" : "Saite nav derīga"}
      </Heading>
      {data.message}
      <StyledLink className="justify-self-center mt-10" showIcon={false} href="/lv">
        <FaHome /> Atgriezties sākumlapā <FaHome />
      </StyledLink>
    </div>
  );
};

export default Page;
