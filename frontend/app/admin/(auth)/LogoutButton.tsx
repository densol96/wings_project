"use client";

import { logout } from "@/actions/auth/logout";
import { Button } from "@/components";

const LogoutButton = () => {
  return <Button onClick={() => logout()}>LOGOUT</Button>;
};

export default LogoutButton;
