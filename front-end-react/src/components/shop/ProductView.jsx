import React from "react";
import Title from "../Title";
import { useParams } from 'react-router-dom';
import { useSingleData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";

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

    const productId = data.result.productId
    const productCategoryId = data.result.productCategory.productCategoryId
    const title = data.result.title
    const description = data.result.description
    const price = data.result.price
    const amount = data.result.amount
    
    return (
        <>
            <p>Hello {id}</p>
            <p>{productId}</p>
            <p>{title}</p>
            <p>{description}</p>
            <p>{price}</p>
            <p>{amount}</p>
            <p>Product category id: {productCategoryId}</p>
        </>
    )
   
}