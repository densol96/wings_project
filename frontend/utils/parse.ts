import { Locale, OrderStatus, PageableResponse, PageableReturn, ShortProductDto } from "@/types";
import clsx from "clsx";
import { ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export const extractIdFromSlug = (slug: string): number => {
  return Number(slug.split("-").at(0)); // 0 specially reserved for "all" category
};

export const extractActiveSegment = (pathname: string) => {
  return pathname.split("/").at(-1);
};

export const parsePageableResponse = <T>(pageableResponse: PageableResponse): PageableReturn<T> => {
  const { content, totalElements, totalPages } = pageableResponse;
  const size = pageableResponse.pageable.pageSize;
  const page = pageableResponse.pageable.pageNumber;
  return { content, page, size, totalPages, totalElements };
};

export const capitalize = (str: string) => str[0].toUpperCase() + str.slice(1).toLowerCase();

export const cn = (...inputs: ClassValue[]) => {
  return twMerge(clsx(inputs));
};

export const removeDiacritics = (str: string) => {
  return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
};

export const createCartItem = (product: ShortProductDto) => {
  return {
    id: product.id,
    title: product.translationDto.title,
    price: product.price,
    image: product.imageDtos.at(0),
    inStockAmount: product.amount,
  };
};

export const pickLabels = <T extends object, K extends keyof T>(dict: T, keys: K[]): Pick<T, K> => {
  return keys.reduce((acc, key) => {
    acc[key] = dict[key];
    return acc;
  }, {} as Pick<T, K>);
};

export const basicErrorText = (lang: Locale = "lv") =>
  lang === "lv" ? "Radās neparedzēta iekšēja kļūda. Lūdzu, mēģiniet vēlreiz vēlāk." : "An unexpected internal error occurred. Please try again later.";

export const normalizeError = (error: unknown, lang: Locale = "lv"): string | undefined => {
  if (!error) return undefined;

  if (Array.isArray(error) && error.length > 0) {
    return normalizeError(error[0]);
  }

  if (typeof error === "object" && error !== null) {
    const values = Object.values(error);
    if (values.length > 0) {
      return normalizeError(values[0]);
    }
  }

  if (typeof error === "string") {
    return error;
  }

  return basicErrorText(lang);
};

export const displayError = (error: string, lang: Locale = "lv") => error || basicErrorText(lang);

export const getFullUrl = (endpoint: string) => (endpoint.startsWith("http") ? endpoint : `${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/${endpoint}`);

export const getOrderStatusColor = (status: OrderStatus): string => {
  switch (status) {
    case OrderStatus.PAID:
      return "text-green-600";
    case OrderStatus.SHIPPED:
      return "text-orange-500";
    default:
      return "text-red-500";
  }
};

export const cleanFormData = (formData: FormData, excludedNames: string[]) => {
  Array.from(formData.entries()).forEach(([name, value]) => {
    if (excludedNames.includes(name)) {
      formData.delete(name);
    } else if (typeof value === "string" && value.trim() === "") {
      formData.delete(name);
    }
  });
};
