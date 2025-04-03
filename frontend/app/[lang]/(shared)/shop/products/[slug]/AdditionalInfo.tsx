import { Heading } from "@/components";
import { ProductDict, ProductDto } from "@/types";
import React from "react";
import InfoItem from "./InfoItem";

type Props = {
  product: ProductDto;
  dict: ProductDict;
};

const AdditionalInfo = ({ product, dict }: Props) => {
  return (
    (product.colorDtos.length > 0 || product.materialDtos.length > 0) && (
      <div className="my-6">
        <Heading size="tiny" as="h2" className="uppercase font-medium mb-2">
          {dict.additionalInformation}
        </Heading>
        <div className="grid grid-cols-2 text-sm">
          <InfoItem label={dict.color} infoDetails={product.colorDtos} getName={(color) => color.name} />
          <InfoItem label={dict.material} infoDetails={product.materialDtos} getName={(material) => `${material.percentage}% ${material.material.name}`} />
        </div>
      </div>
    )
  );
};

export default AdditionalInfo;
