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
