import { Suspense } from "react";
import "./globals.css";

import { Roboto } from "next/font/google";
import { Spinner } from "@/components";
import { Toaster, ToastOptions } from "react-hot-toast";

export const metadata = {
  metadataBase: new URL("https://www.latvijasrokdarbi.lv"),
};

const roboto = Roboto({
  subsets: ["latin"],
  weight: ["400", "500", "700"],
  display: "swap",
});

const toasterSettings: ToastOptions = {
  success: {
    duration: 5000,
  },
  error: {
    duration: 5000,
  },
  style: {
    fontSize: "16px",
    maxWidth: "400px",
    padding: "16px 24px",
    textAlign: "center",
  },
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className={`${roboto.className} min-h-screen flex flex-col`}>
        {/* <Suspense fallback={<Spinner />}>{children}</Suspense> */}
        {children}
        <Toaster position="top-center" gutter={12} containerStyle={{ margin: "8px" }} toastOptions={toasterSettings} />
      </body>
    </html>
  );
}
