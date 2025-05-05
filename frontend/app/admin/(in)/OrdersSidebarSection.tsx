import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const OrdersSidebarSection = ({}: Props) => {
  return (
    <SidebarSection
      title="Pasūtījumi"
      items={[
        { name: "Visi pasūtījumi", href: "/orders" },
        { name: "Atvērti pasūtījumi", href: "/orders/open" },
        { name: "Vēsture", href: "/orders/history" },
      ]}
    />
  );
};

export default OrdersSidebarSection;
