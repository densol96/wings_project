import { locales } from "@/constants/locales";

export const defaultLangIsSelected = (pathname: string): boolean => !locales.some((l) => pathname.startsWith("/" + l.locale));
