"use client";

import { Spinner } from "@/components";
import { useCartContext, useLangContext } from "@/context";
import { useLoadData } from "@/hooks/useLoadData";
import { SummaryLabels } from "@/types";
import { formatPrice } from "@/utils";
import Link from "next/link";
import React, { useEffect } from "react";

type Props = {
  paymentIntentId: string;
  dict: SummaryLabels;
};

export interface OrderSingleProductDto {
  productId: number;
  name: string;
  amount: number;
  price: number;
}

export interface OrderSummaryDto {
  id: number;
  deliverySumup: string;
  discount: number;
  items: OrderSingleProductDto[];
  total: number;
  firstName: string;
  lastName: string;
  email: string;
}

const PaymentInfo = ({ paymentIntentId, dict }: Props) => {
  const { lang } = useLangContext();
  const { clearCart } = useCartContext();
  const { data, isLoading } = useLoadData<OrderSummaryDto>(
    `${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/orders/post-payment/${paymentIntentId}?lang=${lang}`
  );

  useEffect(() => {
    clearCart();
  }, []);

  return isLoading ? (
    <Spinner />
  ) : (
    <div>
      <p>
        <strong className="mr-6">{dict.orderId}:</strong> {data?.id}
      </p>
      <p>
        <strong className="mr-6">{dict.customer}:</strong> {data?.firstName} {data?.lastName} - {data?.email}
      </p>
      <p>
        <strong className="mr-6">{dict.delivery}:</strong> {data?.deliverySumup}
      </p>
      {Boolean(data?.discount) && (
        <p>
          <strong className="mr-6">{dict.discount}:</strong> {formatPrice(data?.discount || 0)}
        </p>
      )}
      <table className="w-full table-auto border border-gray-300 mt-4">
        <thead className="bg-gray-100">
          <tr>
            <th className="px-4 py-2 text-left">{dict.product}</th>
            <th className="px-4 py-2 text-left">{dict.quantity}</th>
            <th className="px-4 py-2 text-left">{dict.price}</th>
            <th className="px-4 py-2 text-left">{dict.total}</th>
          </tr>
        </thead>
        <tbody>
          {data?.items?.map((item) => (
            <tr key={item.productId} className="border-t">
              <td className="px-4 py-2">
                <Link className="underline hover:no-underline" href={`/${lang}/shop/products/${item.productId}`}>
                  {item.name}
                </Link>
              </td>
              <td className="px-4 py-2">{item.amount}</td>
              <td className="px-4 py-2">{formatPrice(item.price)}</td>
              <td className="px-4 py-2">{formatPrice(item.amount * item.price)}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p className="flex justify-end mt-2">
        <strong>
          {dict.totalPrice}: {formatPrice(data?.total || 0)}
        </strong>
      </p>
    </div>
  );
};

export default PaymentInfo;
