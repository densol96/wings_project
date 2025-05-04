import { getUserSessionOrRedirect } from "@/actions/auth/getUserSessionOrRedirect";
import React from "react";
import LogoutButton from "../(auth)/LogoutButton";

const Page = async () => {
  const user = await getUserSessionOrRedirect();
  return (
    <div>
      <div>
        HELLO {user.firstName} {user.lastName}!
      </div>
      <LogoutButton />
    </div>
  );
};

export default Page;
