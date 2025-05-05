import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const ProductsSidebarSection = ({}: Props) => {
  return (
    <SidebarSection
      title="Produkti"
      items={[
        { name: "Produktu saraksts", href: "/products" },
        { name: "Jauns produkts", href: "/products/new" },
        { name: "Produktu kategorijas", href: "/products/categories" },
      ]}
    />
  );
};

export default ProductsSidebarSection;
