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
            <div className="sticky top-44 p-3 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700 m-4">
                <p className="font-semibold text-lg text-center">Kategorijas</p>
                <hr className="mb-2"></hr>
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
                    <button type="button" className="text-center py-2 px-1 w-full text-sm font-medium text-gray-900 focus:outline-none border border-t-indigo-200 bg-white hover:bg-amber-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700 active:bg-amber-600">
                        {data.title}
                    </button>
                </Link>
            </li>
        </>
    )
}