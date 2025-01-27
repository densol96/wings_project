import React from "react";
import Title from "../Title";
import { useAllData } from "../../hooks/dataHooks";
import LoadingSpinner from "../assets/LoadingSpinner";
import { Link } from "react-router-dom";

export default function Categories() {

    const { data, loading, error } = useAllData(
        "http://localhost:8080/api/productcategory/show/all",
    );

    if (loading) {
        return <LoadingSpinner />;
    }

    if (error) {
        return <h1 className="text-3xl text-red-600 text-center">{error}</h1>;
    }


    return (
        <>
            <div class="sticky top-44 p-6 bg-slate-50 border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-10">
                <p><b>Kategorijas</b></p>
                <hr class="mb-2"></hr>
                <ul>
                    {data.result.map((n, i) => {
                        return (
                            <ProductCategory
                                key={n + i}
                                data={n}
                            />
                        );
                    })}
                </ul>
            </div>

        </>
    );
}

function ProductCategory({ data }) {
    return (
        <>
            <li>
                {/* <Link to={{pathname: `/shop/category/${data.title}`, state:{ id: `${data.productCategoryId}`}}}>{data.title}{data.productCategoryId}</Link> */}
                <Link to={`/shop/category/${data.title}?id=${data.productCategoryId}`}>
                    {data.title}
                </Link>
            </li>
        </>
    )
}