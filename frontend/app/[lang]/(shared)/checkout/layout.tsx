import { CheckoutProvider } from "@/context/CheckoutContext";
import React, { ReactNode } from "react";

type Props = {
  children: ReactNode;
};

const Layout = ({ children }: Props) => {
  return <CheckoutProvider>{children}</CheckoutProvider>;
};

export default Layout;
