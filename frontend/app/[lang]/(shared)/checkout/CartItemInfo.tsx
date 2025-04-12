"use client";

import { AmountSelector, MyImage } from "@/components";
import { useCartContext, useLangContext } from "@/context";
import { CartDeliveryRequiredTranslation, CartItem } from "@/types";
import { cn, formatPrice } from "@/utils";
import Link from "next/link";
import React from "react";
import { MdDelete } from "react-icons/md";

type Props = {
  product: CartItem;
  translations: CartDeliveryRequiredTranslation;
};

const DisplayPrice = ({ price, label, className }: { price: number; label: string; className?: string }) => {
  return (
    <div className="flex flex-col items-center justify-between h-full py-3">
      <p className="uppercase tracking-wider font-bold text-gray-600">{label}</p>
      <p className={className}>{formatPrice(price)}</p>
    </div>
  );
};

const CartItemInfo = ({ product, translations }: Props) => {
  const { lang } = useLangContext();
  const { incrementProduct, decrementProduct, removeProduct } = useCartContext();
  const href = `/${lang}/shop/products/${product.id}`;

  return (
    <li className="grid grid-cols-[100px_1fr_auto_auto_auto_auto] gap-x-10 border-2 items-center pr-6">
      <Link href={href} className="w-[100px] h-[100px] relative">
        <MyImage image={product.image} />
      </Link>
      <Link href={href} className="underline hover:no-underline">
        <p className="text-lg">{product.title}</p>
      </Link>

      <DisplayPrice label={translations.price} price={product.price} />
      <div className="flex flex-col items-center">
        <AmountSelector
          className="h-[30px]"
          handleChange={() => {}}
          inStockAmount={product.inStockAmount}
          selectedAmount={product.quantity}
          incr={() => incrementProduct(product.id)}
          decr={() => decrementProduct(product.id)}
        />
        <p className={cn("text-green-700 mt-2")}>{translations.isAvailable.replace("%%AMOUNT%%", product.inStockAmount + "")}</p>
      </div>

      <DisplayPrice className="font-bold text-gray-700" label={translations.total} price={product.price * product.quantity} />

      <button onClick={() => removeProduct(product.id)} className="text-primary-bright hover:text-primary-bright-light transition duration-250">
        <MdDelete size={30} />
      </button>
    </li>
  );
};

export default CartItemInfo;
