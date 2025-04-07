import { Heading, RandomProducts } from "@/components";
import { getDictionary } from "@/dictionaries/dictionaries";
import { PagePropsWithSlug, ProductDict, ProductDto, ShopDict } from "@/types";
import { extractIdFromSlug, fetcher } from "@/utils";

import syncSlug from "@/utils/syncSlug";
import React from "react";
import ProductDisplay from "./ProductDisplay";
import ProductInfo from "./ProductInfo";

type Props = {};

const Page = async ({ params: { lang, slug } }: PagePropsWithSlug) => {
  const productId = extractIdFromSlug(slug);
  const [dictResult, product] = await Promise.all([
    getDictionary(lang),
    fetcher<ProductDto>(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/products/${productId}?lang=${lang}`),
  ]);
  const dict: ProductDict = dictResult.product;
  syncSlug(productId, product.translationDto.title, slug);

  return (
    <>
      <section className="grid grid-cols-1 md:grid-cols-2 gap-10">
        <ProductDisplay lang={lang} images={product.imageDtos} />
        <ProductInfo lang={lang} product={product} dict={dict} />
      </section>
      <section>
        <Heading className="uppercase mt-10 tracking-wider font-medium" size="md">
          {`${dict.relatedProducts}:`}
        </Heading>
        <RandomProducts
          categoryId={product.categoryDto.id}
          amount={12}
          cardOption="related"
          className="w-full my-0 mt-8 mb-20 slider-with-gap"
          lang={lang}
          sliderSettings={{
            slidesToShow: 3,
            responsive: [
              {
                breakpoint: 1024,
                settings: {
                  slidesToShow: 2,
                },
              },
              {
                breakpoint: 768,
                settings: {
                  slidesToShow: 1,
                },
              },
            ],
          }}
          dotsClassname="mt-16"
        />
      </section>
    </>
  );
};

export default Page;
