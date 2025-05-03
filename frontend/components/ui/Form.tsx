import { cn } from "@/utils";
import React, { ReactNode } from "react";

type Props = {
  children: ReactNode;
  cols?: number;
  className?: string;
  action?: (payload: FormData) => void;
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
};

const Form = ({ children, cols = 1, className, action, onSubmit }: Props) => {
  return (
    <form {...(action ? { action } : { onSubmit })} className={cn(`grid md:grid-cols-${cols} gap-6`, className)}>
      {children}
    </form>
  );
};

export default Form;
