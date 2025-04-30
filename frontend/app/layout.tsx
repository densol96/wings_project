import { Suspense } from "react";
import "./globals.css";

import { Roboto } from "next/font/google";
import { Spinner } from "@/components";

export const metadata = {
  metadataBase: new URL("https://www.latvijasrokdarbi.lv"),
};

const roboto = Roboto({
  subsets: ["latin"],
  weight: ["400", "500", "700"],
  display: "swap",
});

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className={`${roboto.className} min-h-screen flex flex-col`}>
        {/* <Suspense fallback={<Spinner />}>{children}</Suspense> */}
        {children}
      </body>
    </html>
  );
}
