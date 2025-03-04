import React from "react";
import RandomProductsSlider from "./RandomProductsSlider";
import Link from "next/link";
import Slider from "react-slick";

const RandomProducts = async () => {
  const data = await fetch("http://localhost:8080/api/products/random");
  const posts = await data.json();
  const randomProducts = posts?.result || [];

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
    randomProducts.length > 0 && (
      <section className="flex justify-center mt-32 mb-40">
        <RandomProductsSlider randomProducts={randomProducts} />
      </section>
    )
  );
};

export default RandomProducts;
