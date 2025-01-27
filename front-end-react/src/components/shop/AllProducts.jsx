import React from "react";
import Title from "../Title";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";
import { Link } from "react-router-dom";
import { CartContext } from "../../CartContext";
import { useContext } from "react";

// TODO add logic for adding all items
// TODO can select items of a certain category

export default function AllProducts() {

    const { data, loading, error } = useAllData(
        "http://localhost:8080/api/products/show/all",
    );

    if (loading) {
        return <LoadingSpinner />;
    }

    if (error) {
        return <h1 className="text-3xl text-red-600 text-center">{error}</h1>;
    }

    return (
        <>

            <div class="grid grid-cols-5">
                {data.result.map((n, i) => {
                    return (
                        <ProductCard
                            key={n + i}
                            data={n}
                        />
                    );
                })}
            </div>

        </>
    );
}

// TODO Change photo logic
function ProductCard({ data }) {

    const cart = useContext(CartContext);
    const productQuantity = cart.getProductQuantity(data.productId)

    return (
        <>
            <div class="block max-w-sm min-h-80 max-h-80 p-6 bg-slate-50 border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-2">
                <Link to={`/shop/show/${data.productId}`}>
                
                    <img class="rounded object-scale-down h-48 w-96 select-none	" src="../src/assets/markusstest.png" alt="" draggable="false"/>

                    <p>{data.title}</p>
                    <p>{data.price}€</p>
                </Link>

                <button onClick={() => cart.addOneToCart(data.productId)} class="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded">
                    {productQuantity}
                </button>

            </div>
        </>
    );
}