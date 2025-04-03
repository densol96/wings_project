"use client";

import { refresh } from "@/actions";
import React from "react";

type Props = {};

const TestBtn = () => {
  return (
    <button className="" onClick={() => refresh()}>
      Refresh
    </button>
  );
};

export default TestBtn;
