import { cn } from "@/utils";
import React, { ReactNode } from "react";

type Props = {
  children: ReactNode;
  cols?: number;
  className?: string;
  onSubmit?: (args: any) => void;
};

const Form = ({ children, cols = 1, className }: Props) => {
  return <form className={cn(`grid md:grid-cols-${cols} gap-6`, className)}>{children}</form>;
};

export default Form;
