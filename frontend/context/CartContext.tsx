"use client";

import { useEffectOnce, useLocalStorage } from "@/hooks";
import { CartItem } from "@/types";
import { createContext, ReactNode, useContext, useEffect, useRef, useState } from "react";
import { useLangContext } from "./LangContext";

export type ProductData = Omit<CartItem, "quantity">;

export type CartContextProps = {
  items: CartItem[];
  addProduct: (product: ProductData, quantity?: number) => void;
  productIsInCart: (id: number) => boolean;
  incrementProduct: (id: number) => void;
  decrementProduct: (id: number) => void;
  removeProduct: (id: number) => void;
  clearCart: () => void;
  getTotalPrice: () => number;
  getTotalNumberOfProducts: () => number;
  cartIsLoaded: boolean;
};

const CartContext = createContext<CartContextProps | undefined>(undefined);

type ProviderProps = {
  children: ReactNode;
};

const STORAGE_KEY = "my_cart_v1";

export const CartProvider = ({ children }: ProviderProps) => {
  const { value: items, updateLocalStorage: setItems } = useLocalStorage<CartItem[]>(STORAGE_KEY, []);
  const [cartIsLoaded, setCartIsLoaded] = useState(false);

  const { lang } = useLangContext();
  const hasRun = useRef(false);

  const updatedProductTitlesInCartPerLocale = async () => {
    // Do not want to make an extra call on mount..
    if (!hasRun.current) {
      hasRun.current = true;
      return;
    }
    const ids = items?.map((item) => item.id)?.join(",") || [];
    try {
      const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL_EXTENDED}/products/localised-titles?lang=${lang}&ids=${ids}`);
      // if a request failed, just show non-translated products in a cart. It's better than showing an error in this case
      if (!response.ok) throw new Error((await response.json()).message);
      const result: {
        id: number;
        title: string;
      }[] = await response.json();
      setItems((prev) => {
        const updated =
          prev?.map((item) => {
            const translated = result.find((t) => t.id === item.id);
            const newTitle = translated?.title || item.title;

            if (item.title !== newTitle) {
              return { ...item, title: newTitle };
            }

            return item;
          }) || [];

        const hasChanges = updated?.some((item, i) => item.title !== prev?.[i]?.title);
        return hasChanges ? updated : prev;
      });
    } catch (e) {
      console.log("Unable to update the cart on a lang changed. Returned response:");
      if (e instanceof Error) {
        console.log(e.message);
      } else {
        console.log("Unknown error:", e);
      }
    }
  };

  useEffect(() => {
    updatedProductTitlesInCartPerLocale();
  }, [items]);

  useEffectOnce(() => {
    setCartIsLoaded(true);
  });

  const addProduct = (product: ProductData, quantity: number = 1) => {
    const availableQty = Math.min(quantity, product.inStockAmount);
    setItems((prev) => {
      const existing = prev?.find((p) => p.id === product.id);
      if (existing) {
        const newQty = Math.min(existing.quantity + availableQty, existing.inStockAmount);
        return prev?.map((p) => (p.id === product.id ? { ...p, quantity: newQty } : p)) || [];
      }
      return [...(prev !== null ? prev : []), { ...product, quantity: availableQty }];
    });
  };

  const productIsInCart = (id: number) => items?.some((p) => p.id === id) || false;

  const incrementProduct = (id: number) => {
    setItems((prev) => prev?.map((p) => (p.id === id && p.quantity < p.inStockAmount ? { ...p, quantity: p.quantity + 1 } : p)) || []);
  };

  const decrementProduct = (id: number) => {
    setItems((prev) => prev?.map((p) => (p.id === id && p.quantity > 1 ? { ...p, quantity: p.quantity - 1 } : p)) || []);
  };

  const removeProduct = (id: number) => {
    setItems((prev) => prev?.filter((p) => p.id !== id) || []);
  };

  const clearCart = () => {
    setItems([]);
  };

  const getTotalPrice = () => {
    return items?.reduce((total, item) => total + item.price * item.quantity, 0) || 0;
  };

  const getTotalNumberOfProducts = () => {
    return items?.reduce((total, item) => total + item.quantity, 0) || 0;
  };

  return (
    <CartContext.Provider
      value={{
        items: items as CartItem[],
        productIsInCart,
        addProduct,
        incrementProduct,
        decrementProduct,
        removeProduct,
        clearCart,
        getTotalPrice,
        getTotalNumberOfProducts,
        cartIsLoaded,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

export const useCartContext = () => {
  const context = useContext(CartContext);
  if (!context) throw new Error("useCartContext must be used within a CartProvider");
  return context;
};
