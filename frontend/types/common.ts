export type Locale = "lv" | "en";

export type Lang = { lang: Locale };

export type PageProps = { params: Lang };

export type PagePropsWithId = {
  params: Lang & {
    id: number;
  };
};

export type PagePropsWithSlug = {
  params: Lang & {
    slug: string;
  };
};

export type ImageDto = {
  src: string;
  alt: string;
};

export type SortDirection = "asc" | "desc";

export type ShortContent = {
  title: string;
  description: string;
};

export type PageInfo = {
  size: number;
  page: number;
  totalElements: number;
  totalPages: number;
};

export type PageableResponse = {
  content: any;
  totalPages: number;
  totalElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
};

export type PageableReturn<T> = {
  content: T[];
  page: number;
  size: number;
  totalPages: number;
  totalElements: number;
};

export type SharedDict = {
  isAlreadyInCart: string;
  total: string;
  price: string;
  isAvailable: string;
  isNotAvailable: string;
  delivery: string;
};

export type CountryCode = "LV" | "LT" | "EE";

export type Country = {
  name: string;
  code: CountryCode;
};

// {name: string ,address : {street: string}} -> {address?: string}
export type ErrorData<T> = {
  [K in keyof T]?: T[K] extends object ? ErrorData<T[K]> : string;
};

export type BasicErrorDto = {
  message: string;
};

export type ButtonProps = {
  children: React.ReactNode;
  className?: string;
  size?: "sm" | "md" | "lg";
  color?: "primary" | "transparent" | "green";
  onClick?: () => Promise<any> | void;
  disabled?: boolean;
  type?: "button" | "submit" | "reset";
  isPending?: boolean;
};

export type HttpMethod = "GET" | "POST" | "PUT" | "PATCH" | "DELETE";

export type TokenProps = {
  params: {
    token: string;
  };
};
