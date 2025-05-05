import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const SecuritySidebarSection = ({}: Props) => {
  return (
    <SidebarSection
      title="Lietotāji un drošība"
      items={[
        { name: "Lietotāju saraksts", href: "/users" },
        { name: "Lomas un tiesības", href: "/security/roles" },
        { name: "Piekļuves iestatījumi", href: "/security/settings" },
      ]}
    />
  );
};
export default SecuritySidebarSection;
