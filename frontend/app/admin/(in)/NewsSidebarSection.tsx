import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const NewsSidebarSection = ({}: Props) => {
  return <SidebarSection title="Jaunumi" items={[{ name: "Jaunumu saraksts", href: "/admin/news" }]} />;
};

export default NewsSidebarSection;
