"use client";

import { createContext, ReactNode, useCallback, useContext, useState } from "react";

export type CartItem = {
  id: number;
  title: string;
  price: number;
  image?: string;
  quantity: number; // added to the cart
  inStockAmount: number; // total avaialable in stock
};

type CartContextType = {
  items: CartItem[];
  addProduct: (product: CartItem, quantity?: number) => void;
  incrementProduct: (id: number) => void;
  decrementProduct: (id: number) => void;
  removeProduct: (id: number) => void;
  clearCart: () => void;
  getTotal: () => number;
};

const CartContext = createContext<CartContextType | undefined>(undefined);

type ProviderProps = {
  children: ReactNode;
};

export const CartProvider = ({ children }: ProviderProps) => {
  const [items, setItems] = useState<CartItem[]>([]);

  const addProduct = (product: CartItem, quantity: number = 1) => {
    const availableQty = Math.min(quantity, product.inStockAmount);
    setItems((prev) => {
      const existing = items.find((p) => p.id === product.id);
      if (existing) {
        const newQty = Math.min(existing.quantity + availableQty, existing.inStockAmount);
        return prev.map((p) => (p.id === product.id ? { ...p, quantity: newQty } : p));
      }
      return [...prev, { ...product, quantity: availableQty }];
    });
  };

  const incrementProduct = (id: number) => {
    setItems((prev) => prev.map((p) => (p.id === id && p.quantity < p.inStockAmount ? { ...p, quantity: p.quantity + 1 } : p)));
  };

  const decrementProduct = (id: number) => {
    setItems((prev) => prev.map((p) => (p.id === id && p.quantity > 1 ? { ...p, quantity: p.quantity - 1 } : p)));
  };

  const removeProduct = (id: number) => {
    setItems((prev) => prev.filter((p) => p.id !== id));
  };

  const clearCart = () => {
    setItems([]);
  };

  const getTotal = () => {
    return items.reduce((total, item) => total + item.price * item.quantity, 0);
  };

  return (
    <CartContext.Provider
      value={{
        items,
        addProduct,
        incrementProduct,
        decrementProduct,
        removeProduct,
        clearCart,
        getTotal,
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
