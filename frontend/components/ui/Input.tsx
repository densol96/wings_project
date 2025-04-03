import React from "react";

type Props = {
  placeholder: string;
};

const Input = ({ placeholder }: Props) => {
  return <input className="py-2 pl-2 outline-none border-1 border-gray-300 rounded-md" placeholder={placeholder} type="text" />;
};

export default Input;
