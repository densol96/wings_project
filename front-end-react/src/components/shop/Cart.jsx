import { CartContext } from "../../CartContext";
import { useContext } from "react";
import { getAllProducts, getProductData } from "./ProductsList";

export default function Cart(props) {

    const { data: products, error } = getAllProducts();

    if (error) return <p>Error: {error.message}</p>;

    const cart = useContext((CartContext));
    const id = props.id;
    const quantity = props.quantity;
    
    const productData = getProductData(id, products);


    if (!productData) return <p>Product not found</p>;
    return (
        <>
            <h3>{productData.title}</h3>
            <p>{quantity} kopā</p>
            <p>cena { (quantity * productData.price).toFixed }</p>
            <button onClick={() => cart.deleteFromCart(id)}>Noņemt</button>
            <button onClick={() => cart.addOneToCart(id)}>Pievienot 1</button>
            <button onClick={() => cart.removeOneFromCart(id)}>Noņemt 1</button>
        </>
    );
}