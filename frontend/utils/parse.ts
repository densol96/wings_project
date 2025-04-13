import { PageableResponse, PageableReturn, ShortProductDto } from "@/types";
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
