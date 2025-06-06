import { createContext, useState } from "react";
import { getAllProducts, getProductData } from "./components/shop/ProductsList";

// https://youtu.be/_8M-YVY76O8?si=hi3JVUb-lKE02iVE

export const CartContext = createContext({
	items: [],
	getProductQuantity: () => {},
	addOneToCart: () => {},
	removeOneFromCart: () => {},
	deleteFromCart: () => {},
	getTotalCost: () => {},
});

export function CartProvider({ children }) {
	const [cartProducts, setCartProducts] = useState([]);
	const { data: products, error } = getAllProducts();

	function getProductQuantity(id) {
		const quantity = cartProducts.find(product => product.id === id)?.quantity;
		if (quantity === undefined) {
			return 0;
		}
		return quantity;
	}

	function addOneToCart(id) {
		const quantity = getProductQuantity(id);
		if (quantity === 0) {
			setCartProducts([
				...cartProducts,
				{
					id: id,
					quantity: 1,
				},
			]);
		} else {
			setCartProducts(
				cartProducts.map(product =>
					product.id === id ? { ...product, quantity: product.quantity + 1 } : product,
				),
			);
		}
	}

	function removeOneFromCart(id) {
		const quantity = getProductQuantity(id);

		if (quantity == 1) {
			deleteFromCart(id);
		} else {
			setCartProducts(
				cartProducts.map(product =>
					product.id === id ? { ...product, quantity: product.quantity - 1 } : product,
				),
			);
		}
	}

	function deleteFromCart(id) {
		setCartProducts(cartProducts =>
			cartProducts.filter(currentProduct => {
				return currentProduct.id != id;
			}),
		);
	}

	function getTotalCost() {
		if (error) return <p>Error: {error.message}</p>;

		let totalCost = 0;
		cartProducts.map(cartItem => {
			const productData = getProductData(cartItem.id, products);
			totalCost += productData.price * cartItem.quantity;
		});
		return totalCost;
	}

	const contextValue = {
		items: cartProducts,
		getProductQuantity,
		addOneToCart,
		removeOneFromCart,
		deleteFromCart,
		getTotalCost,
	};

	return <CartContext.Provider value={contextValue}>{children}</CartContext.Provider>;
}

export default CartProvider;
