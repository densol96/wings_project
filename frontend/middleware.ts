import { NextResponse, type NextRequest } from "next/server";
import { defaultLocale } from "@/constants/locales";
import { i18n } from "@/i18n-config";
import { encryptUser, hasAccess, isProtectedRoute } from "./utils";
import { getUserSession } from "./actions/helpers/getUserSession";

export async function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;
  console.log("MIDDLEWARE RUNS FOR: " + pathname);
  // AUTH
  // avoid i18n rewrite if
  if (pathname.startsWith("/api") || pathname.startsWith("/admin") || pathname.startsWith("/test")) {
    // admin/login ; admin/restore-passsword etc.
    if (!isProtectedRoute(pathname)) {
      return NextResponse.next();
    }

    const token = request.cookies.get("authToken")?.value;
    if (!token) return NextResponse.redirect(new URL("/admin/login", request.url)); // no "session" to start with

    const user = await getUserSession(token);
    if (!user) {
      const resLoginRedirect = NextResponse.redirect(new URL("/admin/login?expired=true", request.url));
      resLoginRedirect.cookies.delete("authToken");
      return resLoginRedirect;
    }

    // Check authenticated user's access rights to specific resource aka MANAGE_NEWS for /news etc.
    if (!hasAccess(pathname, user)) {
      const resUnauthorisedRedirect = NextResponse.redirect(new URL(`/admin/unauthorised?pathname=${pathname}`, request.url));
      return resUnauthorisedRedirect;
    }

    const res = NextResponse.next();
    res.headers.set("X-User", await encryptUser(request.nextUrl.origin, user));
    res.headers.set("X-Current-Url", pathname);
    return res;
  }

  // i18n
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
    "/((?!_next|_actions|.*\\..*).*)",
  ],
};
