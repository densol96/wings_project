"use client";
import React, { useEffect } from "react";

type Props = {
  className?: string;
  children?: React.ReactNode;
};

const Page = () => {
  useEffect(() => {
    (async () => {
      const response = await fetch("http://localhost:8080/test");
      console.log(response);
      const text = await response.text();
      console.log(text);
    })();
  }, []);
  return <img src=""></img>;
};

export default Page;
