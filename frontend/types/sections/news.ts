import { Image, ShortContent } from "@/types/common";

export type ShortNewsItem = {
  id: number;
  image: Image;
  translation: ShortContent;
  createdAt: Date;
};

export type SingleNewsItem = {
  id: number;
  images: Image[];
  translation: ShortContent & {
    location: string;
  };
  createdAt: Date;
  startDate: Date;
  endDate: Date;
  category: string;
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
