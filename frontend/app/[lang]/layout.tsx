import type { Metadata } from "next";
import "./globals.css";
import { AlternateURLs } from "next/dist/lib/metadata/types/alternative-urls-types";
import { Header, Footer } from "@/components";

import { Roboto } from "next/font/google";
import { getDictionary } from "@/dictionaries/dictionaries";

const roboto = Roboto({
  subsets: ["latin"],
  weight: ["400", "500", "700"],
  display: "swap",
});

type Params = { lang: "en" | "lv" };

type Props = {
  params: Params;
  children: React.ReactNode;
};

export async function generateStaticParams() {
  return [{ lang: "en" }, { lang: "lv" }];
}

export async function generateMetadata({ params }: { params: Params }): Promise<Metadata> {
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
}

export default async function RootLayout({ children, params: { lang } }: Props) {
  const dict = await getDictionary(lang);

  return (
    <html lang={lang}>
      <body className={`${roboto.className} min-h-screen flex flex-col`}>
        <Header lang={lang} headerDictionary={dict.header} />
        {children}
        <Footer footerDictionary={dict.footer} />
      </body>
    </html>
  );
}
