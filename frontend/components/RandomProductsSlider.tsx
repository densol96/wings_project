"use client";

import Image from "next/image";
import Link from "next/link";
import React from "react";
import Slider from "react-slick";

type Props = {
  randomProducts: any[];
};

const RandomProductsSlider: React.FC<Props> = ({ randomProducts }) => {
  const settings = {
    infinite: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 4000,
    ltr: true,
  };

  let mp = { x0: 0, y0: 0, x: 0, y: 0, md: false };

  return (
    <Slider {...settings} className="w-1/2 slider-container overflow-hidden shadow-2xl rounded-lg">
      {randomProducts.map(
        (product, i) =>
          product?.productPictures?.[0] && (
            <Link
              className="w-full h-full"
              key={`shop/show/${product.id}`}
              href={`shop/show/${product.id}`}
              onMouseDown={(e) => {
                mp.x0 = mp.x = e.clientX;
                mp.y0 = mp.y = e.clientY;
                mp.md = true;
              }}
            >
              <div key={product.id} className="w-full relative min-h-96">
                <h1 className="absolute left-1/2 -translate-x-1/2 rounded-lg top-5 bg-black bg-opacity-50 text-white text-bold p-4 z-20">
                  {product.title}
                </h1>
                <Image
                  fill
                  className="w-full h-full z-10"
                  src={`${process.env.NEXT_PUBLIC_BACKEND_URL}/images/pasakumi/bilde1.jpg`}
                  alt="Attēls"
                />
              </div>
            </Link>
          )
      )}
    </Slider>
  );
};

export default RandomProductsSlider;
