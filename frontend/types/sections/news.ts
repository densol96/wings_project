import { Image, ShortContent } from "@/types/common";

export type ShortNewsItem = {
  id: number;
  image: Image;
  translation: ShortContent;
  createdAt: Date;
};

export type SingleNewsItem = {
  id: number;
  imageDtos: Image[];
  translationDto: ShortContent & {
    location: string;
  };
  createdAt: Date;
  startDate: Date;
  endDate: Date;
  categoryName: string;
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
