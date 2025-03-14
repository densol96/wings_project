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

export type Image = {
  src: string;
  alt: string;
};

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

export type PageableReturn = {
  content: any;
  page: number;
  size: number;
  totalPages: number;
  totalElements: number;
};
