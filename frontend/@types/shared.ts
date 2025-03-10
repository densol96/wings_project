export type Locale = "lv" | "en";

export type Lang = { lang: Locale };

export type PageProps = { params: Lang };

export type HomeContent = {
  title: string;
  description: string;
  button: string;
  quote: string;
  imageDescription: string;
  lineOneDescription: string;
  lineTwoDescription: string;
};

export type Image = {
  src: string;
  alt: string;
};

export type ShortNewsItem = {
  id: number;
  image: Image;
  translation: {
    title: string;
    description: string;
  };
  createdAt: Date;
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

export type NewsDictionaryType = {
  title: string;
  description: string;
  button: string;
  postedOn: string;
  readMore: string;
  alt: string;
  error: string;
};
