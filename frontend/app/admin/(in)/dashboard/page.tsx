import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import React from "react";
import LogoutButton from "../../(auth)/LogoutButton";

const Page = async () => {
  const user = await getUserSessionOrRedirect();
  return (
    <div>
      <h1>DASHBOARD</h1>
    </div>
  );
};

export default Page;
