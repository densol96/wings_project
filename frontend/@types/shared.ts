export type PageProps = { params: { lang: "lv" | "en" } };

export type HomeContent = {
  title: string;
  description: string;
  button: string;
  quote: string;
  imageDescription: string;
  lineOneDescription: string;
  lineTwoDescription: string;
};

export type ShortNewsItem = {
  id: number;
  title: string;
  description: string;
  startDate: Date;
  endDate: Date;
  imageUrl: string | null;
};

export type PageInfo = {
  size: number;
  number: number;
  totalElements: number;
  totalPages: number;
};
