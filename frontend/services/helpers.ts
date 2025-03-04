import { locales } from "@/constants/locales";

const defaultLangIsSelected = (pathname: string): boolean => !locales.some((l) => pathname.startsWith("/" + l.locale));

export { defaultLangIsSelected };
