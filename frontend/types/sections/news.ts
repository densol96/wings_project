import { ImageDto, ShortContent } from "@/types/common";

export type ShortNewsItem = {
  id: number;
  imageDto: ImageDto;
  translation: ShortContent;
  createdAt: Date;
};

export type SingleNewsItem = {
  id: number;
  imageDtos: ImageDto[];
  translationDto: ShortContent & {
    location?: string;
  };
  createdAt: Date;
  startDate?: Date;
  endDate?: Date;
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
