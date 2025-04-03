import { capitalize } from "@/utils";
import React from "react";

type Props<T> = {
  infoDetails: T[];
  label: string;
  getName: (infoDetail: T) => string;
};

const InfoItem = <T,>({ infoDetails, label, getName }: Props<T>) => {
  return (
    infoDetails.length !== 0 && (
      <>
        <p className="text-gray-500 uppercase font-medium tracking-wide"> {label}</p>
        <p>
          {infoDetails.map((infoDetail, i) => (
            <>
              <a key={i}>{capitalize(getName(infoDetail))}</a>
              {i !== infoDetails.length - 1 && ", "}
            </>
          ))}
        </p>
      </>
    )
  );
};

export default InfoItem;
