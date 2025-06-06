// import "./globals.css";

// import { Roboto } from "next/font/google";
import { AlternateURLs } from "next/dist/lib/metadata/types/alternative-urls-types";

import { getDictionary } from "@/dictionaries/dictionaries";
import { PageProps } from "@/types/common";

import { Header, Footer, NavSidebar } from "@/components/shared";
import TestBtn from "./TestBtn";

import dynamic from "next/dynamic";
// import { Toaster, ToastOptions } from "react-hot-toast";
import Scroll from "@/components/shared/Scroll";
import { LangProvider } from "@/context/LangContext";
import { SidebarProvider } from "@/context/SidebarContext";
import { CartProvider } from "@/context/CartContext";
import { ReactQueryProvider } from "@/context";
const CookiesPopup = dynamic(() => import("@/components/shared/CookiesPopup"), { ssr: false });

// const roboto = Roboto({
//   subsets: ["latin"],
//   weight: ["400", "500", "700"],
//   display: "swap",
// });

export const generateStaticParams = async () => {
  return [{ lang: "en" }, { lang: "lv" }];
};

export const generateMetadata = async ({ params }: PageProps) => {
  const { meta } = await getDictionary(params.lang);

  return {
    title: {
      template: meta.title.template,
      default: meta.title.default,
    },
    description: meta.description,
    openGraph: {
      title: meta.openGraph.title,
      description: meta.openGraph.description,
      url: "https://www.latvijasrokdarbi.lv", // JUST AS AN EXAMPLE FOR NOW
      siteName: meta.openGraph.siteName,
      images: [
        {
          url: "path_to_image.jpg", // JUST AS AN EXAMPLE FOR NOW
          width: 800,
          height: 600,
          alt: "Spārni biedrība",
        },
      ],
      type: "website",
    },
    alternates: {
      lv: { href: "https://example.com/", hreflang: "lv" },
      en: { href: "https://example.com/en", hreflang: "en" },
    } as AlternateURLs,
  };
};

type Props = PageProps & {
  children: React.ReactNode;
};

// const toasterSettings: ToastOptions = {
//   success: {
//     duration: 5000,
//   },
//   error: {
//     duration: 5000,
//   },
//   style: {
//     fontSize: "16px",
//     maxWidth: "400px",
//     padding: "16px 24px",
//     textAlign: "center",
//   },
// };

const RootLayout = async ({ children, params: { lang } }: Props) => {
  const dict = await getDictionary(lang);
  return (
    <LangProvider langValueFromServer={lang}>
      {/* <html lang={lang}> */}
      <Scroll />
      {/* <body className={`${roboto.className} min-h-screen flex flex-col`}> */}
      <ReactQueryProvider>
        {/* <TestBtn /> */}
        <CartProvider>
          <SidebarProvider>
            <Header navMenu={dict.navMenu} />
            <NavSidebar navMenu={dict.navMenu} />
          </SidebarProvider>
          <main className="relative flex-1">{children}</main>
        </CartProvider>
        <Footer footerDictionary={dict.footer} />
        <CookiesPopup dict={dict.cookiesPopup} />
        {/* <Toaster position="top-center" gutter={12} containerStyle={{ margin: "8px" }} toastOptions={toasterSettings} /> */}
      </ReactQueryProvider>
      {/* </body> */}
      {/* </html> */}
    </LangProvider>
  );
};

export default RootLayout;
