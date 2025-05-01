import { NextResponse, type NextRequest } from "next/server";
import { defaultLocale } from "@/constants/locales";
import { i18n } from "@/i18n-config";

export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  if (pathname.startsWith("/api") || pathname.startsWith("/admin")) {
    return NextResponse.next();
  }

  if (pathname.startsWith(`/${defaultLocale}/`) || pathname === `/${defaultLocale}`) {
    // The incoming request is for /lv/whatever, so we'll reDIRECT to /whatever
    const newUrl = new URL(pathname.replace(`/${defaultLocale}`, pathname === `/${defaultLocale}` ? "/" : ""), request.url);
    newUrl.search = request.nextUrl.search;
    return NextResponse.redirect(newUrl);
  }
  const pathnameIsMissingLocale = i18n.locales.every((locale) => !pathname.startsWith(`/${locale}/`) && pathname !== `/${locale}`);
  if (pathnameIsMissingLocale) {
    return NextResponse.rewrite(new URL(`/${defaultLocale}${pathname}${request.nextUrl.search}`, request.nextUrl.href));
  }
}

export const config = {
  matcher: [
    // Exclude internal Next.js paths (_next) and static files (public)
    "/((?!_next|.*\\..*).*)",
  ],
};
