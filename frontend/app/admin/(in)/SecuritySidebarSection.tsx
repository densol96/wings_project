import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const SecuritySidebarSection = ({}: Props) => {
  return (
    <SidebarSection
      title="Darbinieki un drošība"
      items={[
        { name: "Darbinieku saraksts", href: "/admin/security/users" },
        { name: "Lomas un tiesības", href: "/admin/security/roles" },
        { name: "Drošības žurnāls", href: "/admin/security/events" },
      ]}
    />
  );
};
export default SecuritySidebarSection;
