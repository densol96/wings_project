import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const OrdersSidebarSection = ({}: Props) => {
  return <SidebarSection title="Pasūtījumi" items={[{ name: "Visi pasūtījumi", href: "/admin/orders" }]} />;
};

export default OrdersSidebarSection;
