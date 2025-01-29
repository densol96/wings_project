import React from "react";
import { useParams } from 'react-router-dom';
import { useSingleData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";
import { CartContext } from "../../CartContext";
import { useContext } from "react";
import CartModal from "../shop/CartModal";

export default function ProductView() {

    const { id } = useParams();

    const { data, loading, error } = useSingleData(
        `http://localhost:8080/api/products/show/${id}`,
    );

    if (loading) {
        return <LoadingSpinner />;
    }

    if (error) {
        return <h1 className="text-3xl text-red-600 text-center">{error}</h1>;
    }

    const productCategoryId = data.result.productCategory.productCategoryId
    const title = data.result.title
    const description = data.result.description
    const price = data.result.price
    const amount = data.result.amount

    const cart = useContext(CartContext);
    const productQuantity = cart.getProductQuantity(data.result.productId)
    
    return (
        <>
            <div className="sm:grid grid-cols-8 m-10">
                <div className="2xl:col-start-3 2xl:col-span-2 xl:col-start-2 xl:col-span-3 sm:col-start-1 sm:col-span-4">
                    <img className="2xl:ml-0 xl:ml-1 rounded w-[500px] h-[500px] object-fill border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 select-none" src={`../../src/assets/${data.result.description}.jpg`} alt="" draggable="false"/>
                </div>
                <div className="2xl:col-end-7 2xl:col-span-2 xl:col-end-8 xl:col-span-3 sm:col-end-9 sm:col-span-4 sm:pl-6 sm:mt-0 m-2 pl-2 border border-gray-250 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 ml-1">
                
                    <div className="pb-4 pt-1">    
                        <h1 className="font-bold text-2xl">{price}€</h1>
                        <h1 className="font-bold text-lg">{title}</h1>
                    </div>
                    <p className="pb-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <div className="pb-2">
                        <CartModal type={2} product={data.result.productId}/>
                    </div>
                
                </div>
            </div>
            <div className="grid grid-cols-8 m-10 ">
                <div className="md:col-start-3 md:col-span-4 sm:col-start-2 sm:col-span-5 col-start-1 col-span-6">
                    <h1 className="font-bold text-2xl pb-2">Papildus informācija</h1>
                    <h1>Lielums: L</h1>
                    <h1>Krāsa: Sarkana</h1>
                </div>
            </div>
        </>
    )
   
}