import React from "react";

type Props = {
  className?: string;
  children?: React.ReactNode;
};

const Page = ({ searchParams }) => {
  console.log(searchParams);
  return <div className="">Hello world!</div>;
};

export default Page;
