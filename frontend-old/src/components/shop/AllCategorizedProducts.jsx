import React from "react";
import Title from "../Title";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";
import { Link, useParams, useLocation } from "react-router-dom";
import { CartContext } from "../../CartContext";
import { useContext } from "react";

export default function AllCategorizedProducts( {id, search} ) {

    const { data, loading, error } = useAllData(
        `http://localhost:8080/api/products/show/category/${id}`,
    );

    if (loading) {
        return <LoadingSpinner />;
    }

    if (error) {
        console.log(error);
        return <h1 className="text-3xl text-red-600 text-center">{error}</h1>;
    }
    console.log(data);

    return (
        <>
            {data.title}
            <div className="grid 2xl:grid-cols-5 xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-3 sm:grid-cols-2 grid-cols-1">
                {data.result.filter((item) => {
                    return search.toLowerCase() === '' ? item : item.title.toLowerCase().includes(search);
                }).map((n, i) => {
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
    const productQuantity = cart.getProductQuantity(data.id)
console.log(data)
    return (
        <>
            <div className="block max-w-sm min-h-60 max-h-80 p-3 bg-white border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-2 hover:bg-slate-100">
                <Link to={`/shop/show/${data.id}`}>
                    
                    <img className="rounded h-48 w-96 object-fit shadow border border-gray-250 select-none" src={`http://localhost:8080/images/${data.productPictures?.[0]?.referenceToPicture}`} alt="" draggable="false"/>
                    <div className="mt-2 mb-1">
                        <p>{data.title}</p>
                        <p>{data.price}€</p>
                    </div>
                </Link>

                <button onClick={() => cart.addOneToCart(data.id)} className="bg-transparent hover:bg-amber-500 text-amber-500 font-semibold hover:text-white py-2 px-4 border border-amber-500 hover:border-transparent rounded active:bg-amber-600">
                    Pievienot ({productQuantity})
                </button>

            </div>
        </>
    );
}