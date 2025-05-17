import { Button, Form, FormField, Heading, StyledLink } from "@/components";
import SubmitButton from "@/components/ui/SubmitButton";
import { cn } from "@/utils";
import Image from "next/image";
import React from "react";
import { FaHome } from "react-icons/fa";
import { IoLogInSharp } from "react-icons/io5";

type Props = {
  title: string;
  link?: {
    href: string;
    label: string;
    icon: React.ReactNode;
  };
  children: React.ReactNode;
  btnText?: string;
  action?: (payload: FormData) => void;
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
  isPending?: boolean;
  className?: string;
};

const AuthForm = ({ title, link, btnText, children, action, onSubmit, isPending = false, className }: Props) => {
  return (
    <div className={cn("min-w-[400px] sm:min-w-[400px] flex flex-col justify-center mb-5", className)}>
      <div className="w-[80%] mx-auto relative" style={{ aspectRatio: "16 / 9" }}>
        <Image fill className={cn("object-contain")} src="/biedribas_logo.png" alt="Biedribas Logo" />
      </div>
      <Heading size="lg" className="text-center mb-10">
        {title}
      </Heading>
      <Form {...(action ? { action } : { onSubmit })} className="md:gap-6 grid-cols-[1fr] gap-2">
        {children}
        <SubmitButton className="w-full mb-4" isPending={isPending}>
          {btnText || "Apstiprināt"}
        </SubmitButton>
        {link && (
          <StyledLink className="justify-self-center flex items-center gap-2" showIcon={false} href={link.href}>
            {link.icon} {link.label} {link.icon}
          </StyledLink>
        )}
        <StyledLink className="justify-self-center" showIcon={false} href="/lv">
          <FaHome /> Atgriezties sākumlapā <FaHome />
        </StyledLink>
      </Form>
    </div>
  );
};

export default AuthForm;
