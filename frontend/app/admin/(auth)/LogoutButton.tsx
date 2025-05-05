"use client";

import { logout } from "@/actions/auth/logout";
import { Button } from "@/components";
import { IoLogOut } from "react-icons/io5";

const LogoutButton = () => {
  return (
    <Button color="neutral" className="flex items-center gap-2 px-2" onClick={() => logout()}>
      Iziet no sistemas <IoLogOut size={20} />
    </Button>
  );
};

export default LogoutButton;
