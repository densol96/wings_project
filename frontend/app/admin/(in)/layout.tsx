"use server";

import Link from "next/link";
import { Permission } from "@/types";
import Image from "next/image";
import { cn } from "@/utils";
import NewsSidebarSection from "./NewsSidebarSection";
import ProductsSidebarSection from "./ProductsSidebarSection";
import OrdersSidebarSection from "./OrdersSidebarSection";
import SecuritySidebarSection from "./SecuritySidebarSection";
import LogoutButton from "../(auth)/LogoutButton";
import Greeting from "./Greeting";
import AccountButton from "./AccountButton";
import { getCurrentUser } from "@/actions/helpers/getCurrentUser";
import { headers } from "next/headers";

type Props = {
  children: React.ReactNode;
  modal: React.ReactNode;
};

const Layout = async ({ children, modal }: Props) => {
  const user = await getCurrentUser();

  return (
    <>
      <div className="flex min-h-screen bg-gray-50 ">
        <aside className="w-64 bg-gray-200 border-l shadow-sm p-4 space-y-4 border-r-2 border-r-gray-300/75 h-[100vh] overflow-y-auto sticky top-0">
          <Link href="/admin/dashboard" className="w-[80%] mx-auto relative my-10 block" style={{ aspectRatio: "16 / 9" }}>
            <Image fill className={cn("object-contain")} src="/biedribas_logo.png" alt="Biedribas Logo" />
          </Link>
          {user.authorities.includes(Permission.MANAGE_NEWS) && <NewsSidebarSection />}
          {user.authorities.includes(Permission.MANAGE_PRODUCTS) && <ProductsSidebarSection />}
          {user.authorities.includes(Permission.MANAGE_ORDERS) && <OrdersSidebarSection />}
          {user.authorities.includes(Permission.MANAGE_SECURITY) && <SecuritySidebarSection />}
        </aside>
        <main className="flex-1 flex flex-col">
          <header className="h-auto flex justify-end items-center gap-4 px-10 py-4 border-b-2 border-b-gray-300/75 bg-gray-200 sticky top-0 z-20">
            <Greeting user={user} />
            <AccountButton />
            <LogoutButton />
          </header>
          <div className="flex-1 py-20 px-40 relative">{children}</div>
        </main>
      </div>
      {modal}
    </>
  );
};

export default Layout;
