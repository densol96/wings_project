import { ImageDto } from "./common";
import { ShortProductTranslationDto } from "./sections";

export type SliderCardOption = "random" | "related";

export type ResponsiveSettings = {
  breakpoint: 1024 | 768 | 600;
  settings: {
    slidesToShow: number;
  };
};

export type SliderSettings = {
  slidesToShow: number;
  responsive?: ResponsiveSettings[];
};

export type SearchedNewsDto = {
  id: number;
  title: string;
  createdAt: string;
  imageDto: ImageDto;
};

export type SearchedProductDto = {
  id: number;
  price: number;
  amount: number;
  imageDto: ImageDto;
  title: string;
};
