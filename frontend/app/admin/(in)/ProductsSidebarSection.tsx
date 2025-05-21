import React from "react";
import SidebarSection from "./SidebarSection";

type Props = {};

const ProductsSidebarSection = ({}: Props) => {
  return (
    <SidebarSection
      title="Produkti"
      items={[
        { name: "Produktu saraksts", href: "/admin/products" },
        { name: "Produktu kategorijas", href: "/admin/products/categories" },
        { name: "Veco produktu arhivs", href: "/admin/products/deleted" },
      ]}
    />
  );
};

export default ProductsSidebarSection;
